package azure.devops.services.test.runs;

/**
 * @author PC
 * This is a temporary class to provide the details for the test run environment.
 */
public class DtlEnvironmentDetails {

	/**
	 * 
	 */
	String csmContent;
	/**
	 * 
	 */
	String csmParameters;
	/**
	 * 
	 */
	String subscriptionName;
	
	public String getCsmContent() {
		return csmContent;
	}
	public void setCsmContent(String csmContent) {
		this.csmContent = csmContent;
	}
	public String getCsmParameters() {
		return csmParameters;
	}
	public void setCsmParameters(String csmParameters) {
		this.csmParameters = csmParameters;
	}
	public String getSubscriptionName() {
		return subscriptionName;
	}
	public void setSubscriptionName(String subscriptionName) {
		this.subscriptionName = subscriptionName;
	}
}
