package azure.devops.services.test;

/**
 * @author PC
 * Adding test cases to a suite creates one of more test points based on the default configurations and testers assigned to the test suite. 
 * PointAssignment is the list of test points that were created for each of the test cases that were added to the test suite.
 */
public class PointAssignment {

	/**
	 * Configuration that was assigned to the test case.
	 */
	ShallowReference configuration;
	/**
	 * Tester that was assigned to the test case
	 */
	IdentityRef tester;
	
	public ShallowReference getConfiguration() {
		return configuration;
	}
	public void setConfiguration(ShallowReference configuration) {
		this.configuration = configuration;
	}
	public IdentityRef getTester() {
		return tester;
	}
	public void setTester(IdentityRef tester) {
		this.tester = tester;
	}
}
