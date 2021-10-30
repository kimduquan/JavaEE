package azure.devops.services.test.runs;

/**
 * @author PC
 * This class is used to provide the filters used for discovery
 */
public class RunFilter {

	/**
	 * filter for the test case sources (test containers)
	 */
	String sourceFilter;
	/**
	 * filter for the test cases
	 */
	String testCaseFilter;
	
	public String getSourceFilter() {
		return sourceFilter;
	}
	public void setSourceFilter(String sourceFilter) {
		this.sourceFilter = sourceFilter;
	}
	public String getTestCaseFilter() {
		return testCaseFilter;
	}
	public void setTestCaseFilter(String testCaseFilter) {
		this.testCaseFilter = testCaseFilter;
	}
}
