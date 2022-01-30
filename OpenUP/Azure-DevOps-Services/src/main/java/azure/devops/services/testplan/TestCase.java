package azure.devops.services.testplan;

import azure.devops.services.test.PointAssignment;
import azure.devops.services.testplan.configurations.TeamProjectReference;

/**
 * @author PC
 * Test Case Class
 */
public class TestCase {

	/**
	 * Order of the TestCase in the Suite
	 */
	int order;
	/**
	 * List of Points associated with the Test Case
	 */
	PointAssignment[] pointAssignments;
	/**
	 * Project under which the Test Case is
	 */
	TeamProjectReference project;
	/**
	 * Test Plan under which the Test Case is
	 */
	TestPlanReference testPlan;
	/**
	 * Test Suite under which the Test Case is
	 */
	TestSuiteReference testSuite;
	/**
	 * Work Item details of the TestCase
	 */
	WorkItemDetails workItem;
	
	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}
	public PointAssignment[] getPointAssignments() {
		return pointAssignments;
	}
	public void setPointAssignments(PointAssignment[] pointAssignments) {
		this.pointAssignments = pointAssignments;
	}
	public TeamProjectReference getProject() {
		return project;
	}
	public void setProject(TeamProjectReference project) {
		this.project = project;
	}
	public TestPlanReference getTestPlan() {
		return testPlan;
	}
	public void setTestPlan(TestPlanReference testPlan) {
		this.testPlan = testPlan;
	}
	public TestSuiteReference getTestSuite() {
		return testSuite;
	}
	public void setTestSuite(TestSuiteReference testSuite) {
		this.testSuite = testSuite;
	}
	public WorkItemDetails getWorkItem() {
		return workItem;
	}
	public void setWorkItem(WorkItemDetails workItem) {
		this.workItem = workItem;
	}
}
