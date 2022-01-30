package azure.devops.services.test;

/**
 * @author PC
 * List of test results filtered on the basis of GroupByValue
 */
public class TestResultHistoryForGroup {

	/**
	 * Display name of the group.
	 */
	String displayName;
	/**
	 * Name or Id of the group identifier by which results are grouped together.
	 */
	String groupByValue;
	/**
	 * List of results for GroupByValue
	 */
	TestCaseResult[] results;
	
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getGroupByValue() {
		return groupByValue;
	}
	public void setGroupByValue(String groupByValue) {
		this.groupByValue = groupByValue;
	}
	public TestCaseResult[] getResults() {
		return results;
	}
	public void setResults(TestCaseResult[] results) {
		this.results = results;
	}
}
