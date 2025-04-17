package epf.persistence.event;

import epf.schema.utility.EntityEvent;

public class AsyncEvent {

	private transient final EntityEvent event;
	
	public AsyncEvent(final EntityEvent event) {
		this.event = event;
	}

	public EntityEvent getEvent() {
		return event;
	}
}
