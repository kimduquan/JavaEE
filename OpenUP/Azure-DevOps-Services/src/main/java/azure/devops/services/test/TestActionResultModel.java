package azure.devops.services.test;

/**
 * @author PC
 * Represents a test step result.
 */
public class TestActionResultModel {

	/**
	 * Path identifier for test step in test case workitem. 
	 * Note: 
	 * 1) It is represented in Hexadecimal format with 8 digits for a step. 
	 * 2) Internally, the step ID value for first step starts with 2 so actionPath = 00000002 step 9, will have an ID = 10 and actionPath = 0000000a step 15, will have an ID =16 and actionPath = 00000010 
	 * 3) actionPath of shared step is concatenated with the parent step of test case. 
	 * Example, it would be something of type - 0000000300000001 where 00000003 denotes action path of test step and 00000001 denotes action path for shared step
	 */
	String actionPath;
	/**
	 * Comment in result.
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
	 * Error message in result.
	 */
	String errorMessage;
	/**
	 * Iteration ID of test action result.
	 */
	int iterationId;
	/**
	 * Test outcome of result.
	 */
	String outcome;
	/**
	 * Reference to shared step workitem.
	 */
	SharedStepModel sharedStepModel;
	/**
	 * Time when execution started(UTC).
	 */
	String startedDate;
	/**
	 * This is step Id of test case. 
	 * For shared step, it is step Id of shared step in test case workitem; step Id in shared step. 
	 * Example: TestCase workitem has two steps: 
	 * 1) Normal step with Id = 1 
	 * 2) Shared Step with Id = 2. 
	 * Inside shared step: 
	 * 	a) Normal Step with Id = 1 Value for StepIdentifier for First step: "1" Second step: "2;1"
	 */
	String stepIdentifier;
	/**
	 * Url of test action result. Deprecated in hosted environment.
	 */
	String url;
	
	public String getActionPath() {
		return actionPath;
	}
	public void setActionPath(String actionPath) {
		this.actionPath = actionPath;
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
	public int getIterationId() {
		return iterationId;
	}
	public void setIterationId(int iterationId) {
		this.iterationId = iterationId;
	}
	public String getOutcome() {
		return outcome;
	}
	public void setOutcome(String outcome) {
		this.outcome = outcome;
	}
	public SharedStepModel getSharedStepModel() {
		return sharedStepModel;
	}
	public void setSharedStepModel(SharedStepModel sharedStepModel) {
		this.sharedStepModel = sharedStepModel;
	}
	public String getStartedDate() {
		return startedDate;
	}
	public void setStartedDate(String startedDate) {
		this.startedDate = startedDate;
	}
	public String getStepIdentifier() {
		return stepIdentifier;
	}
	public void setStepIdentifier(String stepIdentifier) {
		this.stepIdentifier = stepIdentifier;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
}
