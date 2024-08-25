package epf.messaging.schema;

import org.eclipse.jnosql.mapping.MappedSuperclass;
import jakarta.nosql.Id;

/**
 * 
 */
@MappedSuperclass
public class Destination {
	
	/**
	 * 
	 */
	@Id
	private String id;

	public String getId() {
		return id;
	}

	public void setId(final String id) {
		this.id = id;
	}
}
