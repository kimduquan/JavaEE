package azure.devops.services.test;

/**
 * @author PC
 * Filter to get TestCase result history.
 */
public class TestHistoryQuery {

	/**
	 * Automated test name of the TestCase.
	 */
	String automatedTestName;
	/**
	 * Results to be get for a particular branches.
	 */
	String branch;
	/**
	 * Get the results history only for this BuildDefinitionId. 
	 * This to get used in query GroupBy should be Branch. 
	 * If this is provided, Branch will have no use.
	 */
	int buildDefinitionId;
	/**
	 * It will be filled by server. 
	 * If not null means there are some results still to be get, and we need to call this REST API with this ContinuousToken. 
	 * It is not supposed to be created (or altered, if received from server in last batch) by user.
	 */
	String continuationToken;
	/**
	 * Group the result on the basis of TestResultGroupBy. 
	 * This can be Branch, Environment or null(if results are fetched by BuildDefinitionId)
	 */
	TestResultGroupBy groupBy;
	/**
	 * History to get between time interval MaxCompleteDate and (MaxCompleteDate - TrendDays). 
	 * Default is current date time.
	 */
	String maxCompleteDate;
	/**
	 * Get the results history only for this ReleaseEnvDefinitionId. 
	 * This to get used in query GroupBy should be Environment.
	 */
	int releaseEnvDefinitionId;
	/**
	 * List of TestResultHistoryForGroup which are grouped by GroupBy
	 */
	TestResultHistoryForGroup[] resultsForGroup;
	/**
	 * Get the results history only for this testCaseId. 
	 * This to get used in query to filter the result along with automatedtestname
	 */
	int testCaseId;
	/**
	 * Number of days for which history to collect. 
	 * Maximum supported value is 7 days. Default is 7 days.
	 */
	int trendDays;
	
	public String getAutomatedTestName() {
		return automatedTestName;
	}
	public void setAutomatedTestName(String automatedTestName) {
		this.automatedTestName = automatedTestName;
	}
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	public int getBuildDefinitionId() {
		return buildDefinitionId;
	}
	public void setBuildDefinitionId(int buildDefinitionId) {
		this.buildDefinitionId = buildDefinitionId;
	}
	public String getContinuationToken() {
		return continuationToken;
	}
	public void setContinuationToken(String continuationToken) {
		this.continuationToken = continuationToken;
	}
	public TestResultGroupBy getGroupBy() {
		return groupBy;
	}
	public void setGroupBy(TestResultGroupBy groupBy) {
		this.groupBy = groupBy;
	}
	public String getMaxCompleteDate() {
		return maxCompleteDate;
	}
	public void setMaxCompleteDate(String maxCompleteDate) {
		this.maxCompleteDate = maxCompleteDate;
	}
	public int getReleaseEnvDefinitionId() {
		return releaseEnvDefinitionId;
	}
	public void setReleaseEnvDefinitionId(int releaseEnvDefinitionId) {
		this.releaseEnvDefinitionId = releaseEnvDefinitionId;
	}
	public TestResultHistoryForGroup[] getResultsForGroup() {
		return resultsForGroup;
	}
	public void setResultsForGroup(TestResultHistoryForGroup[] resultsForGroup) {
		this.resultsForGroup = resultsForGroup;
	}
	public int getTestCaseId() {
		return testCaseId;
	}
	public void setTestCaseId(int testCaseId) {
		this.testCaseId = testCaseId;
	}
	public int getTrendDays() {
		return trendDays;
	}
	public void setTrendDays(int trendDays) {
		this.trendDays = trendDays;
	}
}
