package epf.workflow.schema.function;

import jakarta.validation.constraints.NotNull;
import epf.workflow.schema.function.mapping.FunctionTypeConverter;
import jakarta.nosql.Column;
import java.io.Serializable;
import java.util.Map;
import jakarta.nosql.Convert;
import jakarta.nosql.Embeddable;
import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;

@Embeddable
public class FunctionDefinition implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@NotNull
	@Column
	@Description("Unique function name. Must follow the Serverless Workflow Naming Convention")
	private String name;
	
	@NotNull
	@Column
	private String operation;
	
	@Column
	@Description("Defines the function type. Can be either http, openapi, asyncapi, rpc, graphql, odata, expression, or custom.")
	@DefaultValue("openapi")
	@Convert(value = FunctionTypeConverter.class)
	private FunctionType type = FunctionType.openapi;
	
	@Column
	@Description("References an auth definition name to be used to access to resource defined in the operation parameter")
	private String authRef;
	
	@Column
	@Description("Metadata information. Can be used to define custom function information")
	private Map<String, String> metadata;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public FunctionType getType() {
		return type;
	}

	public void setType(FunctionType type) {
		this.type = type;
	}

	public String getAuthRef() {
		return authRef;
	}

	public void setAuthRef(String authRef) {
		this.authRef = authRef;
	}

	public Map<String, String> getMetadata() {
		return metadata;
	}

	public void setMetadata(Map<String, String> metadata) {
		this.metadata = metadata;
	}
}
