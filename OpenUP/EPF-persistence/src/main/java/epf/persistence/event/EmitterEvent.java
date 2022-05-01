package epf.persistence.event;

import epf.schema.utility.EntityEvent;

/**
 * 
 */
public class EmitterEvent {

	/**
	 *
	 */
	private transient final EntityEvent event;
	
	/**
	 * @param event
	 */
	public EmitterEvent(final EntityEvent event) {
		this.event = event;
	}

	public EntityEvent getEvent() {
		return event;
	}
}
