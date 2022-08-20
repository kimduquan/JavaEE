package epf.persistence.internal;

import javax.json.JsonPatch;
import epf.schema.utility.EntityEvent;

/**
 * 
 */
public class EntityTransaction {

	/**
	 *
	 */
	private EntityEvent event;
	
	/**
	 *
	 */
	private Object entityId;
	
	/**
	 *
	 */
	private String diff;

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
}
