package azure.devops.services.testplan;

/**
 * @author PC
 * The test plan reference resource.
 */
public class TestPlanReference {

	/**
	 * ID of the test plan.
	 */
	int id;
	/**
	 * Name of the test plan.
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
