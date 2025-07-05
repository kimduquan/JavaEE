package epf.messaging.schema;

import jakarta.nosql.Id;
import jakarta.nosql.MappedSuperclass;

@MappedSuperclass
public class Destination {
	
	@Id
	private String id;

	public String getId() {
		return id;
	}

	public void setId(final String id) {
		this.id = id;
	}
}
