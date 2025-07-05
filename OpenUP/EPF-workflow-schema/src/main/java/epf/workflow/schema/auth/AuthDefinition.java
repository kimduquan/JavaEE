package epf.workflow.schema.auth;

import jakarta.json.bind.annotation.JsonbTypeAdapter;
import jakarta.validation.constraints.NotNull;
import epf.workflow.schema.auth.adapter.AuthDefinitionAdapter;
import jakarta.nosql.Column;
import java.io.Serializable;
import jakarta.nosql.Embeddable;
import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;

@Embeddable
@JsonbTypeAdapter(value = AuthDefinitionAdapter.class)
public class AuthDefinition implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@NotNull
	@Column
	@Description("Unique auth definition name. Must follow the Serverless Workflow Naming Convention")
	private String name;
	
	@NotNull
	@Column
	@Description("Auth scheme, can be \"basic\", \"bearer\", or \"oauth2\".")
	@DefaultValue("basic")
	private Scheme scheme = Scheme.basic;
	
	@NotNull
	@Column
	@Description("Auth scheme properties. Can be one of \"Basic properties definition\", \"Bearer properties definition\", or \"OAuth2 properties definition\"")
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
