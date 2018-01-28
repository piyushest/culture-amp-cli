package cli.com.entity;

public class StatsDetails {

	private int totalNumberOfResponses;
	private Double totalGrossRating=0.0;

	public int getTotalNumberOfResponses() {
		return totalNumberOfResponses;
	}

	public void setTotalNumberOfResponses(int totalNumberOfResponses) {
		this.totalNumberOfResponses = totalNumberOfResponses;
	}

	public Double getTotalGrossRating() {
		return totalGrossRating;
	}

	public void setTotalGrossRating(double totalGrossRating) {
		this.totalGrossRating = totalGrossRating;
	}
}
