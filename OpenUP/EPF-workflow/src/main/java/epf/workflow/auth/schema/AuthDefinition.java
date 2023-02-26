package epf.workflow.auth.schema;

import javax.json.bind.annotation.JsonbTypeAdapter;
import javax.validation.constraints.NotNull;

import epf.workflow.auth.schema.adapter.AuthDefinitionAdapter;
import jakarta.nosql.mapping.Column;
import jakarta.nosql.mapping.Embeddable;

/**
 * @author PC
 *
 */
@Embeddable
@JsonbTypeAdapter(value = AuthDefinitionAdapter.class)
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
