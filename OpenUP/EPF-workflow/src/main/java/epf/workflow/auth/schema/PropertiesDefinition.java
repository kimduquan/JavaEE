package epf.workflow.auth.schema;

import jakarta.nosql.Column;
import org.eclipse.jnosql.mapping.MappedSuperclass;

/**
 * @author PC
 *
 */
@MappedSuperclass
public class PropertiesDefinition {

	/**
	 * 
	 */
	@Column
	private Object metadata;

	public Object getMetadata() {
		return metadata;
	}

	public void setMetadata(Object metadata) {
		this.metadata = metadata;
	}
}
