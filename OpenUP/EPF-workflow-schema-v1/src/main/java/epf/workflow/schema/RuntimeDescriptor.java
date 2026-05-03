package epf.workflow.schema;

import java.util.Map;
import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

@JsonClassDescription("This argument contains information about the runtime executing the workflow.")
public class RuntimeDescriptor {

	@JsonPropertyDescription("A human friendly name for the runtime.")
	private String name;
	
	@JsonPropertyDescription("The version of the runtime. This can be an arbitrary string")
	private String version;
	
	@JsonPropertyDescription("An object/map of implementation specific key-value pairs. This can be chosen by runtime implementors and usage of this argument signals that a given workflow definition might not be runtime agnostic")
	private Map<?, ?> metadata;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Map<?, ?> getMetadata() {
		return metadata;
	}

	public void setMetadata(Map<?, ?> metadata) {
		this.metadata = metadata;
	}
}
