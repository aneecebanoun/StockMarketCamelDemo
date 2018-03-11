package banoun.aneece.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
@Controller
public class FxController {
	
	@RequestMapping(value = {"","/"}, method = { RequestMethod.GET, RequestMethod.POST })
	public String fxRates(final Model model, final HttpServletRequest request){
		chartSections(model, request);
		return "fxEu";
	}

	private void chartSections(final Model model, final HttpServletRequest request) {
		
		final String baseUrl = request.getRequestURL().substring(0, request.getRequestURL().length()
				- request.getRequestURI().length() + request.getContextPath().length()) + "/";

		final String chartViewUrl = baseUrl + "chartView";
		final String googleUrl = chartViewUrl+"?stockMarketChart=GOOGL"; 
		final String appleUrl = chartViewUrl+"?stockMarketChart=AAPL";
		final String oracleUrl = chartViewUrl+"?stockMarketChart=ORCL"; 
		final String ibmUrl = chartViewUrl+"?stockMarketChart=IBM";
		final String barclaysUrl = chartViewUrl+"?stockMarketChart=BCS"; 
		final String lloydsUrl = chartViewUrl+"?stockMarketChart=LYG";
		final String morganUrl = chartViewUrl+"?stockMarketChart=JPM"; 
		final String citiUrl = chartViewUrl+"?stockMarketChart=C";
		model.addAttribute("googleUrl", googleUrl);
		model.addAttribute("appleUrl", appleUrl);
		model.addAttribute("oracleUrl", oracleUrl);
		model.addAttribute("ibmUrl", ibmUrl);
		model.addAttribute("barclaysUrl", barclaysUrl);
		model.addAttribute("lloydsUrl", lloydsUrl);
		model.addAttribute("morganUrl", morganUrl);
		model.addAttribute("citiUrl", citiUrl);
		model.addAttribute("chartViewUrl", chartViewUrl);
	}

}
