package azure.devops.services.test;

/**
 * @author PC
 * Represents a test iteration result.
 */
public class TestIterationDetailsModel {
	/**
	 * Test step results in an iteration.
	 */
	TestActionResultModel[] actionResults;
	/**
	 * Reference to attachments in test iteration result.
	 */
	TestCaseResultAttachmentModel[] attachments;
	/**
	 * Comment in test iteration result.
	 */
	String comment;
	/**
	 * Time when execution completed(UTC).
	 */
	String completedDate;
	/**
	 * Duration of execution.
	 */
	int durationInMs;
	/**
	 * Error message in test iteration result execution.
	 */
	String errorMessage;
	/**
	 * ID of test iteration result.
	 */
	int id;
	/**
	 * Test outcome if test iteration result.
	 */
	String outcome;
	/**
	 * Test parameters in an iteration.
	 */
	TestResultParameterModel[] parameters;
	/**
	 * Time when execution started(UTC).
	 */
	String startedDate;
	/**
	 * Url to test iteration result.
	 */
	String url;
	
	public TestActionResultModel[] getActionResults() {
		return actionResults;
	}
	public void setActionResults(TestActionResultModel[] actionResults) {
		this.actionResults = actionResults;
	}
	public TestCaseResultAttachmentModel[] getAttachments() {
		return attachments;
	}
	public void setAttachments(TestCaseResultAttachmentModel[] attachments) {
		this.attachments = attachments;
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
	public String getOutcome() {
		return outcome;
	}
	public void setOutcome(String outcome) {
		this.outcome = outcome;
	}
	public TestResultParameterModel[] getParameters() {
		return parameters;
	}
	public void setParameters(TestResultParameterModel[] parameters) {
		this.parameters = parameters;
	}
	public String getStartedDate() {
		return startedDate;
	}
	public void setStartedDate(String startedDate) {
		this.startedDate = startedDate;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
}
