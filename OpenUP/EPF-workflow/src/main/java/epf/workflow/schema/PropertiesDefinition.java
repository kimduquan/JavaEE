package epf.workflow.schema;

import jakarta.nosql.mapping.Column;
import jakarta.nosql.mapping.MappedSuperclass;

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
