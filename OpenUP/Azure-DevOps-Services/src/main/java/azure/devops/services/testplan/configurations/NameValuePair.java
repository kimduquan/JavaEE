package azure.devops.services.testplan.configurations;

/**
 * @author PC
 * Name value pair
 */
public class NameValuePair {

	/**
	 * Name
	 */
	String name;
	/**
	 * Value
	 */
	String value;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
