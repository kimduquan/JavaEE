package epf.query.persistence;

import javax.enterprise.context.RequestScoped;
import epf.schema.utility.EntityEvent;

/**
 * @author PC
 *
 */
@RequestScoped
public class Message {

	/**
	 * 
	 */
	private EntityEvent event;

	public EntityEvent getEvent() {
		return event;
	}

	public void setEvent(final EntityEvent event) {
		this.event = event;
	}
}
