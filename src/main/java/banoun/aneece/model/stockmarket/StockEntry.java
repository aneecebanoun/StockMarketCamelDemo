package banoun.aneece.model.stockmarket;

import java.time.LocalDateTime;

public class StockEntry {

	private String dateTime;
	private LocalDateTime localDateTime;
	private Double average;
	private Double marketAverage;
	
	public String getDateTime() {
		return dateTime;
	}
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
	public LocalDateTime getLocalDateTime() {
		return localDateTime;
	}
	public void setLocalDateTime(LocalDateTime localDateTime) {
		this.localDateTime = localDateTime;
	}
	public Double getAverage() {
		return average;
	}
	public void setAverage(Double average) {
		this.average = average;
	}
	public Double getMarketAverage() {
		return marketAverage;
	}
	public void setMarketAverage(Double marketAverage) {
		this.marketAverage = marketAverage;
	}
	@Override
	public String toString() {
		return "StockEntry [dateTime=" + dateTime + ", localDateTime=" + localDateTime + ", average=" + average
				+ ", marketAverage=" + marketAverage + "]";
	}

}
