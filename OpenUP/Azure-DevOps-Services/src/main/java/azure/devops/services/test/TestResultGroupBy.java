package azure.devops.services.test;

/**
 * @author PC
 * Group the result on the basis of TestResultGroupBy. 
 * This can be Branch, Environment or null(if results are fetched by BuildDefinitionId)
 */
public class TestResultGroupBy {

	/**
	 * Group the results by branches
	 */
	String branch;
	/**
	 * Group the results by environment
	 */
	String environment;
	
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	public String getEnvironment() {
		return environment;
	}
	public void setEnvironment(String environment) {
		this.environment = environment;
	}
}
