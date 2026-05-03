package epf.workflow.schema;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import jakarta.validation.constraints.NotNull;

@JsonClassDescription("Describes a data schema.")
public class Schema {

	@NotNull
	@JsonPropertyDescription("The schema format.")
	private String format = "json";
	
	@JsonPropertyDescription("The inline schema document. Required if resource has not been set, otherwise ignored.")
	private Object document;
	
	@JsonPropertyDescription("The schema external resource. Required if document has not been set, otherwise ignored.")
	private ExternalResource resource;

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public Object getDocument() {
		return document;
	}

	public void setDocument(Object document) {
		this.document = document;
	}

	public ExternalResource getResource() {
		return resource;
	}

	public void setResource(ExternalResource resource) {
		this.resource = resource;
	}
}
