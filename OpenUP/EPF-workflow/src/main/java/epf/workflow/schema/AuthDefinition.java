package epf.workflow.schema;

import javax.validation.constraints.NotNull;
import jakarta.nosql.mapping.Column;
import jakarta.nosql.mapping.Embeddable;

/**
 * @author PC
 *
 */
@Embeddable
public class AuthDefinition {

	/**
	 * 
	 */
	@NotNull
	@Column
	private String name;
	
	/**
	 * 
	 */
	@Column
	private Scheme scheme = Scheme.basic;
	
	/**
	 * 
	 */
	@NotNull
	@Column
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
