package epf.persistence;

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
}
