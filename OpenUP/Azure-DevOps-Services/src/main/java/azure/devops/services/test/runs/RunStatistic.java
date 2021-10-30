package azure.devops.services.test.runs;

/**
 * @author PC
 * Test run statistics per outcome.
 */
public class RunStatistic {

	/**
	 * Test result count fo the given outcome.
	 */
	int count;
	/**
	 * Test result outcome
	 */
	String outcome;
	/**
	 * Test run Resolution State.
	 */
	TestResolutionState resolutionState;
	/**
	 * ResultMetadata for the given outcome/count.
	 */
	ResultMetadata resultMetadata;
	/**
	 * State of the test run
	 */
	String state;
	
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getOutcome() {
		return outcome;
	}
	public void setOutcome(String outcome) {
		this.outcome = outcome;
	}
	public TestResolutionState getResolutionState() {
		return resolutionState;
	}
	public void setResolutionState(TestResolutionState resolutionState) {
		this.resolutionState = resolutionState;
	}
	public ResultMetadata getResultMetadata() {
		return resultMetadata;
	}
	public void setResultMetadata(ResultMetadata resultMetadata) {
		this.resultMetadata = resultMetadata;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
}
