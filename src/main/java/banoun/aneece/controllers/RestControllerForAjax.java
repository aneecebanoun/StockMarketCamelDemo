package banoun.aneece.controllers;
import static banoun.aneece.services.ServiceUtilities.*;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import banoun.aneece.model.stockmarket.StockEntry;
import banoun.aneece.model.stockmarket.StockSymbol;

@RestController
public class RestControllerForAjax {


	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MMMM/yyyy (HH:mm)");
	
	@PostMapping("/currentStockMarketAjax")
	public Map<String, String> currentStockMarketAjax() {
		final StringBuffer updateTime = new StringBuffer();
		final Map<String, String> result = new HashMap<>();
		StockSymbol.SYMBOLS_MAP.keySet().stream().forEach(symbol->{
			final StockSymbol stockSymbol = StockSymbol.SYMBOLS_MAP.get(symbol);
			final List<StockEntry> sortedDayHistory = stockSymbol.getSortedDayHistory();
			if("".equals(updateTime.toString()) && !sortedDayHistory.isEmpty()){
				updateTime.append(sortedDayHistory.get(0).getLocalDateTime().format(formatter));
			}
			final Double diff = Double.parseDouble(stockSymbol.getCurrentPrice())-stockSymbol.dayAverage();
			final String formattedDiff = String.format("%.2f", diff);
			final String diffDisplay = "0.00".equals(formattedDiff)?SAME:diff>0?UP+formattedDiff:DOWN+formattedDiff;
			result.put(stockSymbol.getName().replaceAll(" ", ""), OPEN_BRAKET+stockSymbol.getName()+" "+diffDisplay+CLOSE_BRAKET);
			final String colour = "0.00".equals(formattedDiff)?"color:blue":diff>0?"color:green":"color:red";
			result.put(stockSymbol.getName().replaceAll(" ", "")+"Color", colour);
		});
		final String stockUpdateTimeId = "Updated on: "+updateTime.toString();
		result.put("stockUpdateTimeId", stockUpdateTimeId);
		return result;
	}

}
