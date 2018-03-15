package banoun.aneece.services;

import static banoun.aneece.services.ServiceUtilities.isNull;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import banoun.aneece.model.stockmarket.StockEntry;
import banoun.aneece.model.stockmarket.StockSymbol;

public class StockMarketProcessor implements Processor {

	private String symbol;

	public StockMarketProcessor(final String symbol) {
		this.symbol = symbol;
	}

	@Override
	public void process(Exchange exchange) throws Exception {
		processMarketSymbol(StockSymbol.SYMBOLS_MAP.get(symbol), exchange);
	}

	@SuppressWarnings("unchecked")
	private void processMarketSymbol(final StockSymbol stockSymbol, final Exchange exchange)
			throws IOException, JsonParseException, JsonMappingException {
		final String jsonString = exchange.getIn().getBody(String.class);
		stockSymbol.setDayHistory(converter(new ObjectMapper().readValue(jsonString, List.class)));
		adjustStockSymbol(stockSymbol);
	}

	private void adjustStockSymbol(final StockSymbol stockSymbol) {
		final String currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern(StockSymbol.DATE_PATTERN));
		stockSymbol.setLastUpdatTime(currentTime);
		stockSymbol.setDayHistoryLastUpdatTime(currentTime);
		final List<StockEntry> sortedDayHistory = stockSymbol.getSortedDayHistory();
		stockSymbol.setCurrentPrice(sortedDayHistory.get(0).getAverage().toString());
		stockSymbol.setPreviousPrice(sortedDayHistory.get(1).getAverage().toString());
	}

	private List<StockEntry> converter(final List<Map<String, Object>> symbolEntries) {
		final List<StockEntry> entries = new ArrayList<>();
		symbolEntries.stream().forEach(symbolEntry -> {
			if (isValidEntry(symbolEntry)) {
				entries.add(getSymbolEntry(symbolEntry));
			}
		});
		return entries;
	}

	private StockEntry getSymbolEntry(final Map<String, Object> symbolEntry) {
		final StockEntry stockEntry = new StockEntry();
		final String time = symbolEntry.get("date").toString() + symbolEntry.get("minute").toString();
		stockEntry.setDateTime(time);
		stockEntry.setLocalDateTime(LocalDateTime.parse(time, DateTimeFormatter.ofPattern(StockSymbol.DATE_PATTERN)));
		stockEntry.setAverage(Double.parseDouble(symbolEntry.get("average").toString()));
		stockEntry.setMarketAverage(Double.parseDouble(symbolEntry.get("marketAverage").toString()));
		return stockEntry;
	}

	private boolean isValidEntry(final Map<String, Object> symbolEntry) {
		return !isNull(symbolEntry.get("date"), symbolEntry.get("minute"), symbolEntry.get("average"))
				&& Double.parseDouble(symbolEntry.get("average").toString()) != 0.0;
	}

}
