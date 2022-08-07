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
	
	/**
	 * 
	 */
	private String tenant;
	
	/**
	 * 
	 */
	private String schema;

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

	public String getTenant() {
		return tenant;
	}

	public void setTenant(final String tenant) {
		this.tenant = tenant;
	}

	public String getSchema() {
		return schema;
	}

	public void setSchema(final String schema) {
		this.schema = schema;
	}
}
