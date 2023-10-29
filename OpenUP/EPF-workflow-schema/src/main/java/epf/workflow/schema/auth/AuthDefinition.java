package epf.workflow.schema.auth;

import jakarta.json.bind.annotation.JsonbTypeAdapter;
import jakarta.validation.constraints.NotNull;
import epf.workflow.schema.auth.adapter.AuthDefinitionAdapter;
import jakarta.nosql.Column;
import java.io.Serializable;
import org.eclipse.jnosql.mapping.Embeddable;

/**
 * @author PC
 *
 */
@Embeddable
@JsonbTypeAdapter(value = AuthDefinitionAdapter.class)
public class AuthDefinition implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
	@NotNull
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
