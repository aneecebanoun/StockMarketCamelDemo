package banoun.aneece.services;

import java.awt.BasicStroke;

import java.awt.Color;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.TimeSeriesCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

import banoun.aneece.model.stockmarket.StockSymbol;

@Service
@RequestScope
public class LineChartViewService {

	@Autowired
	LineChartDataService chartDataService;
	
	public JFreeChart stockMarketChart(final String com) {
		final String title = StockSymbol.SYMBOLS_MAP.get(com).getDescription();
		final TimeSeriesCollection currencyDataSets = chartDataService.stockMarketChart(com);
		final JFreeChart chart = ChartFactory.createTimeSeriesChart(title, "", "Average Price", currencyDataSets, true, true, false);
		final XYPlot plot = (XYPlot) chart.getPlot();
		stylePlotAndChart(plot, chart);
		styleLineChart(plot, currencyDataSets, com);
		return chart;
	}
	
	private void stylePlotAndChart(final XYPlot plot, final JFreeChart chart){
		final Color forgroundColor = Color.BLACK;
		chart.setTextAntiAlias( false );
	    chart.setAntiAlias( false );
	    plot.getRangeAxis().setAutoRange(true);
		plot.setDomainGridlinePaint(forgroundColor);
		plot.setRangeGridlinePaint(forgroundColor);
		plot.setBackgroundPaint(invertColor(forgroundColor));
		chart.setBackgroundPaint(invertColor(forgroundColor));
		chart.getTitle().setPaint(forgroundColor);
	
		plot.getDomainAxis().setLabelPaint(forgroundColor);
		plot.getDomainAxis().setTickLabelPaint(forgroundColor);
		plot.getDomainAxis().setTickMarkPaint(forgroundColor);
		plot.getDomainAxis().setAxisLinePaint(forgroundColor);
		
		plot.getRangeAxis().setLabelPaint(forgroundColor);
		plot.getRangeAxis().setTickLabelPaint(forgroundColor);
		plot.getRangeAxis().setTickMarkPaint(forgroundColor);
		plot.getRangeAxis().setAxisLinePaint(forgroundColor);
	}

	private void styleLineChart(final XYPlot plot, final TimeSeriesCollection currencyDataSets, String type) {
		for (int i = 0; i < currencyDataSets.getSeries().size(); i++) {
			plot.getRendererForDataset(currencyDataSets).setSeriesStroke(i, new BasicStroke(2.3f));
			plot.getRendererForDataset(currencyDataSets).setSeriesPaint(i, chartDataService.stockMarketColor(type));
		}
	}

	private Color invertColor(Color color){
		return new Color(255-color.getRed(), 255-color.getGreen(), 255-color.getBlue());
	}

}
