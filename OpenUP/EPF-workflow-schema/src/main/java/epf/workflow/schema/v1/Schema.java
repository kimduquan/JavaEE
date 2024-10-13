package epf.workflow.schema.v1;

import org.eclipse.microprofile.graphql.Description;
import jakarta.validation.constraints.NotNull;

@Description("Describes a data schema.")
public class Schema {

	@NotNull
	@Description("The schema format.")
	private String format;
	
	@Description("The inline schema document.")
	private Object document;
	
	@Description("The schema external resource.")
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
