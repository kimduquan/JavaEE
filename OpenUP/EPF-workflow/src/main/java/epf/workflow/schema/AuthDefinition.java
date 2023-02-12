package epf.workflow.schema;

/**
 * @author PC
 *
 */
public class AuthDefinition {

	/**
	 * 
	 */
	private String name;
	
	/**
	 * 
	 */
	private Scheme scheme = Scheme.basic;
	
	/**
	 * 
	 */
	private PropertiesDefinition properties;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Scheme getScheme() {
		return scheme;
	}

	public void setScheme(Scheme scheme) {
		this.scheme = scheme;
	}

	public PropertiesDefinition getProperties() {
		return properties;
	}

	public void setProperties(PropertiesDefinition properties) {
		this.properties = properties;
	}
}
