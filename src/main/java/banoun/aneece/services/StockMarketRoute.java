package banoun.aneece.services;

import static banoun.aneece.services.ServiceUtilities.isNull;

import java.io.IOException;
import java.time.LocalDateTime;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import banoun.aneece.model.stockmarket.StockEntry;
import banoun.aneece.model.stockmarket.StockSymbol;

@Service
public class StockMarketRoute extends RouteBuilder {

	private static final String STOCK_URL = "https://api.iextrading.com/1.0/stock/STOCKSYMBOL/chart/1d";
	private static final String STOCK_SYMBOL = "STOCKSYMBOL";
	public static final String WAIT_TIME = Long.toString(1*60*1000L);

	@Override
	public void configure() throws Exception {
		StockSymbol.SYMBOLS_MAP.keySet().stream().forEach(symbol -> {
			StockSymbol stockSymbol = StockSymbol.SYMBOLS_MAP.get(symbol);
			from("timer:stock?period="+WAIT_TIME).enrich(STOCK_URL.replace(STOCK_SYMBOL, symbol)).process(exchange -> {
				processMarketSymbol(stockSymbol, exchange);
			});
		});
	}

	private void processMarketSymbol(final StockSymbol stockSymbol, final Exchange exchange)
			throws IOException, JsonParseException, JsonMappingException {
		final LocalDateTime currentLocalDateTime = LocalDateTime.now();
		final String currentTime = currentLocalDateTime.format(DateTimeFormatter.ofPattern(StockSymbol.DATE_PATTERN));
		stockSymbol.setLastUpdatTime(currentTime);
		stockSymbol.setDayHistoryLastUpdatTime(currentTime);
		final String jsonString = exchange.getIn().getBody(String.class);
		final List<StockEntry> entries = converter(new ObjectMapper().readValue(jsonString, List.class));
		stockSymbol.setDayHistory(entries);
		final List<StockEntry> sortedDayHistory = stockSymbol.getSortedDayHistory();
		stockSymbol.setCurrentPrice(sortedDayHistory.get(0).getAverage().toString());
		stockSymbol.setPreviousPrice(sortedDayHistory.get(1).getAverage().toString());
	}

	private List<StockEntry> converter(final List<Map<String, Object>> symbolEntries) {
		final List<StockEntry> entries = new ArrayList<>();
		symbolEntries.stream().forEach(symbolEntry -> {
			if(!isNull(symbolEntry.get("date"), symbolEntry.get("minute"), symbolEntry.get("average")) && Double.parseDouble(symbolEntry.get("average").toString())!=0.0){
				final StockEntry stockEntry = new StockEntry();
				final String time = symbolEntry.get("date").toString() + symbolEntry.get("minute").toString();
				stockEntry.setDateTime(time);
				stockEntry.setLocalDateTime(LocalDateTime.parse(time, DateTimeFormatter.ofPattern(StockSymbol.DATE_PATTERN)));
				stockEntry.setAverage(Double.parseDouble(symbolEntry.get("average").toString()));
				stockEntry.setMarketAverage(Double.parseDouble(symbolEntry.get("marketAverage").toString()));
				entries.add(stockEntry);
			}
		});
		return entries;
	}
}