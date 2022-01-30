package azure.devops.services.testplan;

/**
 * @author PC
 * The test suite reference resource.
 */
public class TestSuiteReference {

	/**
	 * ID of the test suite.
	 */
	int id;
	/**
	 * Name of the test suite.
	 */
	String name;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
