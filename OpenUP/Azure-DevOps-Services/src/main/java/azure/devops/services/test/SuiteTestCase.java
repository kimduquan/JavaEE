package azure.devops.services.test;

/**
 * @author PC
 * Test case for the suite.
 */
public class SuiteTestCase {

	/**
	 * Point Assignment for test suite's test case.
	 */
	PointAssignment[] pointAssignments;
	/**
	 * Test case workItem reference.
	 */
	WorkItemReference testCase;
	
	public PointAssignment[] getPointAssignments() {
		return pointAssignments;
	}
	public void setPointAssignments(PointAssignment[] pointAssignments) {
		this.pointAssignments = pointAssignments;
	}
	public WorkItemReference getTestCase() {
		return testCase;
	}
	public void setTestCase(WorkItemReference testCase) {
		this.testCase = testCase;
	}
}
