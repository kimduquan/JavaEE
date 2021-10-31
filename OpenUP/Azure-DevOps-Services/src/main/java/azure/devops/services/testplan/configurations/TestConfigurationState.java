package azure.devops.services.testplan.configurations;

/**
 * @author PC
 * State of the configuration
 */
public class TestConfigurationState {

	/**
	 * The configuration can be used for new test runs.
	 */
	String active;
	/**
	 * The configuration has been retired and should not be used for new test runs.
	 */
	String inactive;
	
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	public String getInactive() {
		return inactive;
	}
	public void setInactive(String inactive) {
		this.inactive = inactive;
	}
}
