/**
 * 
 */
package epf.schema;

import java.time.Instant;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.PostLoad;
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
	private transient Event<epf.schema.PostLoad> loadEvent;
	
	/**
	 * 
	 */
	@Inject
	private transient Event<epf.schema.PostPersist> persistEvent;
	
	/**
	 * 
	 */
	@Inject
	private transient Event<epf.schema.PostRemove> removeEvent;
	
	/**
	 * 
	 */
	@Inject
	private transient Event<epf.schema.PostUpdate> updateEvent;

	/**
	 * @param entity
	 */
	@PostLoad
	protected void postLoad(final Object entity) {
		final epf.schema.PostLoad event = new epf.schema.PostLoad();
		event.setTime(Instant.now().toEpochMilli());
		event.setEntity(entity);
		loadEvent.fire(event);
	}
	
	/**
	 * @param entity
	 */
	@PostPersist
	protected void postPersist(final Object entity) {
		final epf.schema.PostPersist event = new epf.schema.PostPersist();
		event.setTime(Instant.now().toEpochMilli());
		event.setEntity(entity);
		persistEvent.fire(event);
	}
	
	/**
	 * @param entity
	 */
	@PostRemove
	protected void postRemove(final Object entity) {
		final epf.schema.PostRemove event = new epf.schema.PostRemove();
		event.setTime(Instant.now().toEpochMilli());
		event.setEntity(entity);
		removeEvent.fire(event);
	}
	
	/**
	 * @param entity
	 */
	@PostUpdate
	protected void postUpdate(final Object entity) {
		final epf.schema.PostUpdate event = new epf.schema.PostUpdate();
		event.setTime(Instant.now().toEpochMilli());
		event.setEntity(entity);
		updateEvent.fire(event);
	}
}
