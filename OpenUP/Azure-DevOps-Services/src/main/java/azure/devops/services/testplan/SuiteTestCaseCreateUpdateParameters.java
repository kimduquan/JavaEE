package azure.devops.services.testplan;

/**
 * @author PC
 * Create and Update Suite Test Case Parameters
 */
public class SuiteTestCaseCreateUpdateParameters {

	/**
	 * Configurations Ids
	 */
	Configuration[] pointAssignments;
	/**
	 * Id of Test Case to be updated or created
	 */
	WorkItem workItem;
	
	public Configuration[] getPointAssignments() {
		return pointAssignments;
	}
	public void setPointAssignments(Configuration[] pointAssignments) {
		this.pointAssignments = pointAssignments;
	}
	public WorkItem getWorkItem() {
		return workItem;
	}
	public void setWorkItem(WorkItem workItem) {
		this.workItem = workItem;
	}
}
