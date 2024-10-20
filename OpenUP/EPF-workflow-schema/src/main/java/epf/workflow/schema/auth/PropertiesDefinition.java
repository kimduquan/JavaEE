package epf.workflow.schema.auth;

import jakarta.nosql.Column;
import java.io.Serializable;
import java.util.Map;
import org.eclipse.jnosql.mapping.MappedSuperclass;
import org.eclipse.microprofile.graphql.Description;

@MappedSuperclass
public class PropertiesDefinition implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column
	@Description("Metadata information")
	private Map<String, String> metadata;

	public Map<String, String> getMetadata() {
		return metadata;
	}

	public void setMetadata(Map<String, String> metadata) {
		this.metadata = metadata;
	}
}
