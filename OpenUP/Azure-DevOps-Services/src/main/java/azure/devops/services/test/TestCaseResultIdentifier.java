package azure.devops.services.test;

/**
 * @author PC
 * Reference to a test result.
 */
public class TestCaseResultIdentifier {

	/**
	 * Test result ID.
	 */
	int testResultId;
	/**
	 * Test run ID.
	 */
	int testRunId;
	
	public int getTestResultId() {
		return testResultId;
	}
	public void setTestResultId(int testResultId) {
		this.testResultId = testResultId;
	}
	public int getTestRunId() {
		return testRunId;
	}
	public void setTestRunId(int testRunId) {
		this.testRunId = testRunId;
	}
}
