package azure.devops.services.test;

/**
 * @author PC
 * Test parameter information in a test iteration.
 */
public class TestResultParameterModel {

	/**
	 * Test step path where parameter is referenced.
	 */
	String actionPath;
	/**
	 * Iteration ID.
	 */
	int iterationId;
	/**
	 * Name of parameter.
	 */
	String parameterName;
	/**
	 * This is step Id of test case. 
	 * For shared step, it is step Id of shared step in test case workitem; step Id in shared step. 
	 * Example: TestCase workitem has two steps: 
	 * 1) Normal step with Id = 1 
	 * 2) Shared Step with Id = 2. Inside shared step: 
	 * a) Normal Step with Id = 1 Value for StepIdentifier for First step: "1" Second step: "2;1"
	 */
	String stepIdentifier;
	/**
	 * Url of test parameter. 
	 * Deprecated in hosted environment.
	 */
	String url;
	/**
	 * Value of parameter.
	 */
	String value;
	
	public String getActionPath() {
		return actionPath;
	}
	public void setActionPath(String actionPath) {
		this.actionPath = actionPath;
	}
	public int getIterationId() {
		return iterationId;
	}
	public void setIterationId(int iterationId) {
		this.iterationId = iterationId;
	}
	public String getParameterName() {
		return parameterName;
	}
	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
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
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
