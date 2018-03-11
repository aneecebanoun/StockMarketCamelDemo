package banoun.aneece.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;
import org.jfree.chart.JFreeChart;

@Service
@RequestScope
public class LineChartService {
	
	@Autowired
	public LineChartViewService chartViewService;
	
	public JFreeChart getStockMarketJFreeChart(final String com){
 		return chartViewService.stockMarketChart(com);
	}

}
