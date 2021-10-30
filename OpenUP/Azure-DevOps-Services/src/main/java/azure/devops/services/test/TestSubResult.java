package azure.devops.services.test;

/**
 * @author PC
 * Represents a sub result of a test result.
 */
public class TestSubResult {

	/**
	 * Comment in sub result.
	 */
	String comment;
	/**
	 * Time when test execution completed(UTC).
	 */
	String completedDate;
	/**
	 * Machine where test executed.
	 */
	String computerName;
	/**
	 * Reference to test configuration.
	 */
	ShallowReference configuration;
	/**
	 * Additional properties of sub result.
	 */
	CustomTestField[] customFields;
	/**
	 * Name of sub result.
	 */
	String displayName;
	/**
	 * Duration of test execution.
	 */
	int durationInMs;
	/**
	 * Error message in sub result.
	 */
	String errorMessage;
	/**
	 * ID of sub result.
	 */
	int id;
	/**
	 * Time when result last updated(UTC).
	 */
	String lastUpdatedDate;
	/**
	 * Outcome of sub result.
	 */
	String outcome;
	/**
	 * Immediate parent ID of sub result.
	 */
	int parentId;
	/**
	 * Hierarchy type of the result, default value of None means its leaf node.
	 */
	ResultGroupType resultGroupType;
	/**
	 * Index number of sub result.
	 */
	int sequenceId;
	/**
	 * Stacktrace.
	 */
	String stackTrace;
	/**
	 * Time when test execution started(UTC).
	 */
	String startedDate;
	/**
	 * List of sub results inside a sub result, if ResultGroupType is not None, it holds corresponding type sub results.
	 */
	TestSubResult[] subResults;
	/**
	 * Reference to test result.
	 */
	TestCaseResultIdentifier testResult;
	/**
	 * Url of sub result.
	 */
	String url;
	
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getCompletedDate() {
		return completedDate;
	}
	public void setCompletedDate(String completedDate) {
		this.completedDate = completedDate;
	}
	public String getComputerName() {
		return computerName;
	}
	public void setComputerName(String computerName) {
		this.computerName = computerName;
	}
	public ShallowReference getConfiguration() {
		return configuration;
	}
	public void setConfiguration(ShallowReference configuration) {
		this.configuration = configuration;
	}
	public CustomTestField[] getCustomFields() {
		return customFields;
	}
	public void setCustomFields(CustomTestField[] customFields) {
		this.customFields = customFields;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public int getDurationInMs() {
		return durationInMs;
	}
	public void setDurationInMs(int durationInMs) {
		this.durationInMs = durationInMs;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getLastUpdatedDate() {
		return lastUpdatedDate;
	}
	public void setLastUpdatedDate(String lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}
	public String getOutcome() {
		return outcome;
	}
	public void setOutcome(String outcome) {
		this.outcome = outcome;
	}
	public int getParentId() {
		return parentId;
	}
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}
	public ResultGroupType getResultGroupType() {
		return resultGroupType;
	}
	public void setResultGroupType(ResultGroupType resultGroupType) {
		this.resultGroupType = resultGroupType;
	}
	public int getSequenceId() {
		return sequenceId;
	}
	public void setSequenceId(int sequenceId) {
		this.sequenceId = sequenceId;
	}
	public String getStackTrace() {
		return stackTrace;
	}
	public void setStackTrace(String stackTrace) {
		this.stackTrace = stackTrace;
	}
	public String getStartedDate() {
		return startedDate;
	}
	public void setStartedDate(String startedDate) {
		this.startedDate = startedDate;
	}
	public TestSubResult[] getSubResults() {
		return subResults;
	}
	public void setSubResults(TestSubResult[] subResults) {
		this.subResults = subResults;
	}
	public TestCaseResultIdentifier getTestResult() {
		return testResult;
	}
	public void setTestResult(TestCaseResultIdentifier testResult) {
		this.testResult = testResult;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
}
