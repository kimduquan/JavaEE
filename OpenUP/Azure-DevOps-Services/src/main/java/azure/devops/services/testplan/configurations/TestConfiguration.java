package azure.devops.services.testplan.configurations;

/**
 * @author PC
 * Test configuration
 */
public class TestConfiguration {

	/**
	 * 
	 */
	String description;
	/**
	 * 
	 */
	int id;
	/**
	 * 
	 */
	boolean isDefault;
	/**
	 * 
	 */
	String name;
	/**
	 * 
	 */
	TeamProjectReference project;
	/**
	 * 
	 */
	TestConfigurationState state;
	/**
	 * 
	 */
	NameValuePair[] values;
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public boolean isDefault() {
		return isDefault;
	}
	public void setDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public TeamProjectReference getProject() {
		return project;
	}
	public void setProject(TeamProjectReference project) {
		this.project = project;
	}
	public TestConfigurationState getState() {
		return state;
	}
	public void setState(TestConfigurationState state) {
		this.state = state;
	}
	public NameValuePair[] getValues() {
		return values;
	}
	public void setValues(NameValuePair[] values) {
		this.values = values;
	}
}
