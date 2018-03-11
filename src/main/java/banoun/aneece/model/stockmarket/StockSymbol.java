package banoun.aneece.model.stockmarket;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StockSymbol {

	public static final String DATE_PATTERN = "yyyyMMddHH:mm";
	
	private static final String[] SYMBOLS = {"AAPL:Apple Inc.:Nasdaq Global Select:Technology:Apple", "GOOGL:Alphabet Inc. (Google):Nasdaq Global Select:Technology:Google", 
			"ORCL:Oracle Corporation:New York Stock Exchange:Technology:Oracle", "IBM:IBM (International Business Machines Corporation):New York Stock Exchange:Technology:IBM",
			"BCS:Barclays PLC:New York Stock Exchange:Financial Services:Barclays", "LYG:Lloyds Banking Group Plc America:New York Stock Exchange:Financial Services:Lloyds US",
			"JPM:JP Morgan Chase & Co.:New York Stock Exchange:Financial Services:JP Morgan", "C:Citigroup Inc.:New York Stock Exchange:Financial Services:Citi"};

	public static final Map<String, StockSymbol> SYMBOLS_MAP;
	
	static{
		SYMBOLS_MAP = new ConcurrentHashMap<>();
		Arrays.asList(SYMBOLS).stream().forEach(e->{
			StockSymbol stockSymbol = new StockSymbol();
			stockSymbol.setSymbol(e.split(":")[0]);
			stockSymbol.setDescription(e.split(":")[1]);
			stockSymbol.setStockDescription(e.split(":")[2]);
			stockSymbol.setSector(e.split(":")[3]);
			stockSymbol.setName(e.split(":")[4]);
			SYMBOLS_MAP.put(stockSymbol.getSymbol(), stockSymbol);
			});
	}
	
	private String symbol = "";
	private String description = "";
	private String name = "";
	private String stockDescription = "";
	private String sector = "";
	private String lastUpdatTime = "";
	private String currentPrice = "0";
	private String previousPrice = "0";
	private String dayHistoryLastUpdatTime = "";
	private Double dayAverage = 0d;
	private List<StockEntry> dayHistory = new ArrayList<>();
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLastUpdatTime() {
		return lastUpdatTime;
	}
	public void setLastUpdatTime(String lastUpdatTime) {
		this.lastUpdatTime = lastUpdatTime;
	}
	public String getCurrentPrice() {
		return currentPrice;
	}
	public void setCurrentPrice(String currentPrice) {
		this.currentPrice = currentPrice;
	}
	public String getPreviousPrice() {
		return previousPrice;
	}
	public void setPreviousPrice(String previousPrice) {
		this.previousPrice = previousPrice;
	}
	public List<StockEntry> getDayHistory() {
		return dayHistory;
	}
	
	public Double dayAverage(){
		return dayAverage;
	}
	
	public List<StockEntry> getSortedDayHistory() {

		List<StockEntry> sorted = dayHistory.stream()
				.sorted((e1, e2)->e2.getLocalDateTime().isAfter(e1.getLocalDateTime())?1:-1)
				.collect(Collectors.toList());
		setGraphAverage();
		return sorted;
	}
	public List<StockEntry> getReverseSortedDayHistory() {
		return dayHistory.stream().sorted((e1, e2)->e1.getLocalDateTime().isAfter(e2.getLocalDateTime())?1:-1).collect(Collectors.toList());
	}

	public void setDayHistory(List<StockEntry> dayHistory) {
		dayAverage = dayHistory.stream().map(e->
		e.getAverage()).reduce(0.0, (e1,e2) -> e1+e2)/dayHistory.size();
		this.dayHistory = dayHistory;
	}
	
	public void setGraphAverage() {
		List<StockEntry> dayHistory = getReverseSortedDayHistory();
		List<Double> averageDiff = new ArrayList<>();
		dayHistory.stream().sorted((e1, e2)->{
			averageDiff.add(e1.getAverage()-e2.getAverage());
			return 0;
			}).collect(Collectors.toList());
	}

	
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getStockDescription() {
		return stockDescription;
	}
	public void setStockDescription(String stockDescription) {
		this.stockDescription = stockDescription;
	}
	public String getSector() {
		return sector;
	}
	public void setSector(String sector) {
		this.sector = sector;
	}
	
	public String getDayHistoryLastUpdatTime() {
		return dayHistoryLastUpdatTime;
	}
	public void setDayHistoryLastUpdatTime(String dayHistoryLastUpdatTime) {
		this.dayHistoryLastUpdatTime = dayHistoryLastUpdatTime;
	}
	
	@Override
	public String toString() {
		return "StockSymbol [symbol=" + symbol + ", description=" + description + ", stockDescription="
				+ stockDescription + ", sector=" + sector + ", lastUpdatTime=" + lastUpdatTime + ", currentPrice="
				+ currentPrice + ", previousPrice=" + previousPrice + ", dayHistoryLastUpdatTime="
				+ dayHistoryLastUpdatTime + ", dayHistory=" + dayHistory + "]";
	}

}
