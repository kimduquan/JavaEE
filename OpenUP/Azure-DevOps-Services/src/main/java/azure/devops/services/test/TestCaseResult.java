package azure.devops.services.test;

/**
 * @author PC
 * Represents a test result.
 */
public class TestCaseResult {

	/**
	 * Test attachment ID of action recording.
	 */
	int afnStripId;
	/**
	 * Reference to area path of test.
	 */
	ShallowReference area;
	/**
	 * Reference to bugs linked to test result.
	 */
	ShallowReference[] associatedBugs;
	/**
	 * ID representing test method in a dll.
	 */
	String automatedTestId;
	/**
	 * Fully qualified name of test executed.
	 */
	String automatedTestName;
	/**
	 * Container to which test belongs.
	 */
	String automatedTestStorage;
	/**
	 * Type of automated test.
	 */
	String automatedTestType;
	/**
	 * TypeId of automated test.
	 */
	String automatedTestTypeId;
	/**
	 * Shallow reference to build associated with test result.
	 */
	ShallowReference build;
	/**
	 * Reference to build associated with test result.
	 */
	BuildReference buildReference;
	/**
	 * Comment in a test result with maxSize= 1000 chars.
	 */
	String comment;
	/**
	 * Time when test execution completed(UTC). 
	 * Completed date should be greater than StartedDate.
	 */
	String completedDate;
	/**
	 * Machine name where test executed.
	 */
	String computerName;
	/**
	 * Reference to test configuration. 
	 * Type ShallowReference.
	 */
	ShallowReference configuration;
	/**
	 * Timestamp when test result created(UTC).
	 */
	String createdDate;
	/**
	 * Additional properties of test result.
	 */
	CustomTestField[] customFields;
	/**
	 * Duration of test execution in milliseconds. 
	 * If not provided value will be set as CompletedDate - StartedDate
	 */
	int durationInMs;
	/**
	 * Error message in test execution.
	 */
	String errorMessage;
	/**
	 * Information when test results started failing.
	 */
	FailingSince failingSince;
	/**
	 * Failure type of test result. 
	 * Valid Value= (Known Issue, New Issue, Regression, Unknown, None)
	 */
	String failureType;
	/**
	 * ID of a test result.
	 */
	int id;
	/**
	 * Test result details of test iterations used only for Manual Testing.
	 */
	TestIterationDetailsModel[] iterationDetails;
	/**
	 * Reference to identity last updated test result.
	 */
	IdentityRef lastUpdatedBy;
	/**
	 * Last updated datetime of test result(UTC).
	 */
	String lastUpdatedDate;
	/**
	 * Test outcome of test result. 
	 * Valid values = (Unspecified, None, Passed, Failed, Inconclusive, Timeout, Aborted, Blocked, NotExecuted, Warning, Error, NotApplicable, Paused, InProgress, NotImpacted)
	 */
	String outcome;
	/**
	 * Reference to test owner.
	 */
	IdentityRef owner;
	/**
	 * Priority of test executed.
	 */
	int priority;
	/**
	 * Reference to team project.
	 */
	ShallowReference project;
	/**
	 * Shallow reference to release associated with test result.
	 */
	ShallowReference release;
	/**
	 * Reference to release associated with test result.
	 */
	ReleaseReference releaseReference;
	/**
	 * ResetCount.
	 */
	int resetCount;
	/**
	 * Resolution state of test result.
	 */
	String resolutionState;
	/**
	 * ID of resolution state.
	 */
	int resolutionStateId;
	/**
	 * Hierarchy type of the result, default value of None means its leaf node.
	 */
	ResultGroupType resultGroupType;
	/**
	 * Revision number of test result.
	 */
	int revision;
	/**
	 * Reference to identity executed the test.
	 */
	IdentityRef runBy;
	/**
	 * Stacktrace with maxSize= 1000 chars.
	 */
	String stackTrace;
	/**
	 * Time when test execution started(UTC).
	 */
	String startedDate;
	/**
	 * State of test result. 
	 * Type TestRunState.
	 */
	String state;
	/**
	 * List of sub results inside a test result, if ResultGroupType is not None, it holds corresponding type sub results.
	 */
	TestSubResult[] subResults;
	/**
	 * Reference to the test executed.
	 */
	ShallowReference testCase;
	/**
	 * Reference ID of test used by test result. Type TestResultMetaData
	 */
	int testCaseReferenceId;
	/**
	 * TestCaseRevision Number.
	 */
	int testCaseRevision;
	/**
	 * Name of test.
	 */
	String testCaseTitle;
	/**
	 * Reference to test plan test case workitem is part of.
	 */
	ShallowReference testPlan;
	/**
	 * Reference to the test point executed.
	 */
	ShallowReference testPoint;
	/**
	 * Reference to test run.
	 */
	ShallowReference testRun;
	/**
	 * Reference to test suite test case workitem is part of.
	 */
	ShallowReference testSuite;
	/**
	 * Url of test result.
	 */
	String url;
	
	public int getAfnStripId() {
		return afnStripId;
	}
	public void setAfnStripId(int afnStripId) {
		this.afnStripId = afnStripId;
	}
	public ShallowReference getArea() {
		return area;
	}
	public void setArea(ShallowReference area) {
		this.area = area;
	}
	public ShallowReference[] getAssociatedBugs() {
		return associatedBugs;
	}
	public void setAssociatedBugs(ShallowReference[] associatedBugs) {
		this.associatedBugs = associatedBugs;
	}
	public String getAutomatedTestId() {
		return automatedTestId;
	}
	public void setAutomatedTestId(String automatedTestId) {
		this.automatedTestId = automatedTestId;
	}
	public String getAutomatedTestName() {
		return automatedTestName;
	}
	public void setAutomatedTestName(String automatedTestName) {
		this.automatedTestName = automatedTestName;
	}
	public String getAutomatedTestStorage() {
		return automatedTestStorage;
	}
	public void setAutomatedTestStorage(String automatedTestStorage) {
		this.automatedTestStorage = automatedTestStorage;
	}
	public String getAutomatedTestType() {
		return automatedTestType;
	}
	public void setAutomatedTestType(String automatedTestType) {
		this.automatedTestType = automatedTestType;
	}
	public String getAutomatedTestTypeId() {
		return automatedTestTypeId;
	}
	public void setAutomatedTestTypeId(String automatedTestTypeId) {
		this.automatedTestTypeId = automatedTestTypeId;
	}
	public ShallowReference getBuild() {
		return build;
	}
	public void setBuild(ShallowReference build) {
		this.build = build;
	}
	public BuildReference getBuildReference() {
		return buildReference;
	}
	public void setBuildReference(BuildReference buildReference) {
		this.buildReference = buildReference;
	}
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
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public CustomTestField[] getCustomFields() {
		return customFields;
	}
	public void setCustomFields(CustomTestField[] customFields) {
		this.customFields = customFields;
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
	public FailingSince getFailingSince() {
		return failingSince;
	}
	public void setFailingSince(FailingSince failingSince) {
		this.failingSince = failingSince;
	}
	public String getFailureType() {
		return failureType;
	}
	public void setFailureType(String failureType) {
		this.failureType = failureType;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public TestIterationDetailsModel[] getIterationDetails() {
		return iterationDetails;
	}
	public void setIterationDetails(TestIterationDetailsModel[] iterationDetails) {
		this.iterationDetails = iterationDetails;
	}
	public IdentityRef getLastUpdatedBy() {
		return lastUpdatedBy;
	}
	public void setLastUpdatedBy(IdentityRef lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
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
	public IdentityRef getOwner() {
		return owner;
	}
	public void setOwner(IdentityRef owner) {
		this.owner = owner;
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	public ShallowReference getProject() {
		return project;
	}
	public void setProject(ShallowReference project) {
		this.project = project;
	}
	public ShallowReference getRelease() {
		return release;
	}
	public void setRelease(ShallowReference release) {
		this.release = release;
	}
	public ReleaseReference getReleaseReference() {
		return releaseReference;
	}
	public void setReleaseReference(ReleaseReference releaseReference) {
		this.releaseReference = releaseReference;
	}
	public int getResetCount() {
		return resetCount;
	}
	public void setResetCount(int resetCount) {
		this.resetCount = resetCount;
	}
	public String getResolutionState() {
		return resolutionState;
	}
	public void setResolutionState(String resolutionState) {
		this.resolutionState = resolutionState;
	}
	public int getResolutionStateId() {
		return resolutionStateId;
	}
	public void setResolutionStateId(int resolutionStateId) {
		this.resolutionStateId = resolutionStateId;
	}
	public ResultGroupType getResultGroupType() {
		return resultGroupType;
	}
	public void setResultGroupType(ResultGroupType resultGroupType) {
		this.resultGroupType = resultGroupType;
	}
	public int getRevision() {
		return revision;
	}
	public void setRevision(int revision) {
		this.revision = revision;
	}
	public IdentityRef getRunBy() {
		return runBy;
	}
	public void setRunBy(IdentityRef runBy) {
		this.runBy = runBy;
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
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public TestSubResult[] getSubResults() {
		return subResults;
	}
	public void setSubResults(TestSubResult[] subResults) {
		this.subResults = subResults;
	}
	public ShallowReference getTestCase() {
		return testCase;
	}
	public void setTestCase(ShallowReference testCase) {
		this.testCase = testCase;
	}
	public int getTestCaseReferenceId() {
		return testCaseReferenceId;
	}
	public void setTestCaseReferenceId(int testCaseReferenceId) {
		this.testCaseReferenceId = testCaseReferenceId;
	}
	public int getTestCaseRevision() {
		return testCaseRevision;
	}
	public void setTestCaseRevision(int testCaseRevision) {
		this.testCaseRevision = testCaseRevision;
	}
	public String getTestCaseTitle() {
		return testCaseTitle;
	}
	public void setTestCaseTitle(String testCaseTitle) {
		this.testCaseTitle = testCaseTitle;
	}
	public ShallowReference getTestPlan() {
		return testPlan;
	}
	public void setTestPlan(ShallowReference testPlan) {
		this.testPlan = testPlan;
	}
	public ShallowReference getTestPoint() {
		return testPoint;
	}
	public void setTestPoint(ShallowReference testPoint) {
		this.testPoint = testPoint;
	}
	public ShallowReference getTestRun() {
		return testRun;
	}
	public void setTestRun(ShallowReference testRun) {
		this.testRun = testRun;
	}
	public ShallowReference getTestSuite() {
		return testSuite;
	}
	public void setTestSuite(ShallowReference testSuite) {
		this.testSuite = testSuite;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
}
