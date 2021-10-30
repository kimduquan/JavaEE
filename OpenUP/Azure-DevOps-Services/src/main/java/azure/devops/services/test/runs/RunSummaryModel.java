package azure.devops.services.test.runs;

/**
 * @author PC
 * Run summary for each output type of test.
 */
public class RunSummaryModel {

	/**
	 * Total time taken in milliseconds.
	 */
	int duration;
	/**
	 * Number of results for Outcome TestOutcome
	 */
	int resultCount;
	/**
	 * Summary is based on outcome
	 */
	TestOutcome testOutcome;
	
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public int getResultCount() {
		return resultCount;
	}
	public void setResultCount(int resultCount) {
		this.resultCount = resultCount;
	}
	public TestOutcome getTestOutcome() {
		return testOutcome;
	}
	public void setTestOutcome(TestOutcome testOutcome) {
		this.testOutcome = testOutcome;
	}
}
