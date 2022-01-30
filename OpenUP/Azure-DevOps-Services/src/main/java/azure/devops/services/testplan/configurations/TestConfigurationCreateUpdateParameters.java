package azure.devops.services.testplan.configurations;

/**
 * @author PC
 * Test Configuration Create or Update Parameters
 */
public class TestConfigurationCreateUpdateParameters {

	/**
	 * Description of the configuration
	 */
	String description;
	/**
	 * Is the configuration a default for the test plans
	 */
	boolean isDefault;
	/**
	 * Name of the configuration
	 */
	String name;
	/**
	 * State of the configuration
	 */
	TestConfigurationState state;
	/**
	 * Dictionary of Test Variable, Selected Value
	 */
	NameValuePair[] values;
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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
