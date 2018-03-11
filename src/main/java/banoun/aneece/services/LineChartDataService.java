package banoun.aneece.services;
import java.awt.Color;
import java.time.ZoneId;
import java.util.Date;

import java.util.List;
import java.util.stream.Collectors;
import org.jfree.data.time.Minute;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;
import banoun.aneece.model.stockmarket.StockEntry;
import banoun.aneece.model.stockmarket.StockSymbol;

@Service
@RequestScope
public class LineChartDataService {

	
	public TimeSeriesCollection stockMarketChart(final String com){
		final TimeSeriesCollection timeSeriesCollection = new TimeSeriesCollection();
		StockSymbol stockSymbol = StockSymbol.SYMBOLS_MAP.get(com);
		final TimeSeries series = new TimeSeries("("+stockSymbol.getSymbol()+") "+stockSymbol.getStockDescription()+ "; "+stockSymbol.getSector());
		stockSymbol.getDayHistory().stream().forEach(stockEntry->{
			stockEntry.getAverage();
			stockEntry.getLocalDateTime();
			Long time = stockEntry.getLocalDateTime().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
			series.add(new Minute(new Date(time)), stockEntry.getAverage());
		});
		timeSeriesCollection.addSeries(series);

		return timeSeriesCollection;
	}
	
	public Color stockMarketColor(String com){
		StockSymbol stockSymbol = StockSymbol.SYMBOLS_MAP.get(com);
		List<StockEntry> stockEntries = stockSymbol.getSortedDayHistory();
		Double minuteDiff = stockEntries.isEmpty()||stockEntries.size()<1?0.00:stockEntries.get(0).getAverage()-stockSymbol.dayAverage();
		Color color = null;
		if(minuteDiff!=null){
			String formattedDiff = String.format("%.2f", minuteDiff);
			color = "0.00".equals(formattedDiff)?Color.BLUE:minuteDiff>0?Color.GREEN:Color.RED;
		}
		return color;
	}

}
