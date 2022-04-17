package epf.schema.utility;

import java.time.Instant;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;

/**
 * @author PC
 *
 */
@ApplicationScoped
public class EntityListener {
	
	/**
	 * 
	 */
	@Inject
	transient Event<epf.schema.utility.PostPersist> persistEvent;
	
	/**
	 * 
	 */
	@Inject
	transient Event<epf.schema.utility.PostRemove> removeEvent;
	
	/**
	 * 
	 */
	@Inject
	transient Event<epf.schema.utility.PostUpdate> updateEvent;
	
	/**
	 * @param entity
	 */
	@PostPersist
	protected void postPersist(final Object entity) {
		final epf.schema.utility.PostPersist event = new epf.schema.utility.PostPersist();
		event.setTime(Instant.now().toEpochMilli());
		event.setEntity(entity);
		persistEvent.fire(event);
	}
	
	/**
	 * @param entity
	 */
	@PostRemove
	protected void postRemove(final Object entity) {
		final epf.schema.utility.PostRemove event = new epf.schema.utility.PostRemove();
		event.setTime(Instant.now().toEpochMilli());
		event.setEntity(entity);
		removeEvent.fire(event);
	}
	
	/**
	 * @param entity
	 */
	@PostUpdate
	protected void postUpdate(final Object entity) {
		final epf.schema.utility.PostUpdate event = new epf.schema.utility.PostUpdate();
		event.setTime(Instant.now().toEpochMilli());
		event.setEntity(entity);
		updateEvent.fire(event);
	}
}
