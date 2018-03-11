package banoun.aneece;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

import banoun.aneece.servlets.ChartViewServlet;

@SpringBootApplication
public class StockMarketCamelDemoApplication {

	@Bean
	public ChartViewServlet chartViewServlet(){
		return new ChartViewServlet();
	}
	
	@Bean
	public ServletRegistrationBean chartViewServletBean(){
	    return new ServletRegistrationBean(chartViewServlet(),"/chartView");
	}

	public static void main(String[] args) {
		SpringApplication.run(StockMarketCamelDemoApplication.class, args);
	}
}
