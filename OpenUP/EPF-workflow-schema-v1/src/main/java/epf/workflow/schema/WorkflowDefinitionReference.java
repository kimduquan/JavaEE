package epf.workflow.schema;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import jakarta.validation.constraints.NotNull;

@JsonClassDescription("References a workflow definition.")
public class WorkflowDefinitionReference {

	@NotNull
	@JsonPropertyDescription("The name of the referenced workflow definition.")
	private String name;
	
	@NotNull
	@JsonPropertyDescription("The namespace of the referenced workflow definition.")
	private String namespace;
	
	@NotNull
	@JsonPropertyDescription("The semantic version of the referenced workflow definition.")
	private String version;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
}
