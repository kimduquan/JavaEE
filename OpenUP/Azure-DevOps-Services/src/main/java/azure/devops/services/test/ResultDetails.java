package azure.devops.services.test;

/**
 * @author PC
 * Details to include with test results. 
 * Default is None. 
 * Other values are Iterations, WorkItems and SubResults.
 */
public class ResultDetails {

	/**
	 * Test iteration details in a test result.
	 */
	String iterations;
	/**
	 * Core fields of test result. 
	 * Core fields includes State, Outcome, Priority, AutomatedTestName, AutomatedTestStorage, Comments, ErrorMessage etc.
	 */
	String none;
	/**
	 * Point and plan detail in a test result.
	 */
	String point;
	/**
	 * Subresults in a test result.
	 */
	String subResults;
	/**
	 * Workitems associated with a test result.
	 */
	String workItems;
	
	public String getIterations() {
		return iterations;
	}
	public void setIterations(String iterations) {
		this.iterations = iterations;
	}
	public String getNone() {
		return none;
	}
	public void setNone(String none) {
		this.none = none;
	}
	public String getPoint() {
		return point;
	}
	public void setPoint(String point) {
		this.point = point;
	}
	public String getSubResults() {
		return subResults;
	}
	public void setSubResults(String subResults) {
		this.subResults = subResults;
	}
	public String getWorkItems() {
		return workItems;
	}
	public void setWorkItems(String workItems) {
		this.workItems = workItems;
	}
}
