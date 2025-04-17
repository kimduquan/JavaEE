package epf.persistence.internal;

import java.io.Serializable;
import jakarta.json.bind.annotation.JsonbTypeAdapter;
import epf.schema.utility.EntityEvent;
import epf.util.json.ext.Adapter;

public class EntityTransaction implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String id;

	@JsonbTypeAdapter(Adapter.class)
	private EntityEvent event;
	
	private Object entityId;
	
	private String diff;

	public String getId() {
		return id;
	}

	public EntityEvent getEvent() {
		return event;
	}

	public void setEvent(final EntityEvent event) {
		this.event = event;
	}

	public Object getEntityId() {
		return entityId;
	}

	public void setEntityId(final Object entityId) {
		this.entityId = entityId;
	}

	public String getDiff() {
		return diff;
	}

	public void setDiff(final String diff) {
		this.diff = diff;
	}

	public void setId(final String id) {
		this.id = id;
	}
}
