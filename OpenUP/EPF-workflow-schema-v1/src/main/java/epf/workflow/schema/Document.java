package epf.workflow.schema;

import java.util.Map;
import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import jakarta.validation.constraints.NotNull;

@JsonClassDescription("Documents the workflow definition.")
public class Document {

	@NotNull
	@JsonPropertyDescription("The version of the DSL used to define the workflow.")
	private String dsl;
	
	@NotNull
	@JsonPropertyDescription("The workflow's namespace.")
	private String namespace;
	
	@NotNull
	@JsonPropertyDescription("The workflow's name.")
	private String name;
	
	@NotNull
	@JsonPropertyDescription("The workflow's semantic version")
	private String version;
	
	@JsonPropertyDescription("The workflow's title.")
	private String title;
	
	@JsonPropertyDescription("The workflow's Markdown summary.")
	private String summary;
	
	@JsonPropertyDescription("A key/value mapping of the workflow's tags, if any.")
	private Map<String, String> tags;
	
	@JsonPropertyDescription("Additional information about the workflow.")
	private Map<?, ?> metadata;

	public String getDsl() {
		return dsl;
	}

	public void setDsl(String dsl) {
		this.dsl = dsl;
	}

	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public Map<String, String> getTags() {
		return tags;
	}

	public void setTags(Map<String, String> tags) {
		this.tags = tags;
	}

	public Map<?, ?> getMetadata() {
		return metadata;
	}

	public void setMetadata(Map<?, ?> metadata) {
		this.metadata = metadata;
	}
}