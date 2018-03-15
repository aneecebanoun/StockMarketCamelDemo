package banoun.aneece.services;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Service;
import banoun.aneece.model.stockmarket.StockSymbol;

@Service
public class StockMarketRoute extends RouteBuilder {

	private static final String STOCK_URL = "https://api.iextrading.com/1.0/stock/STOCKSYMBOL/chart/1d";
	private static final String STOCK_SYMBOL = "STOCKSYMBOL";
	public static final String WAIT_TIME = Long.toString(1*10*1000L);

	@Override
	public void configure() throws Exception {
		StockSymbol.SYMBOLS_MAP.keySet().stream().forEach(symbol -> {
			route(symbol);
		});
	}

	private void route(final String symbol) {
		from("timer:stock?period="+WAIT_TIME)
		.enrich(STOCK_URL.replace(STOCK_SYMBOL, symbol))
		.process(new StockMarketProcessor(symbol));
	}

}