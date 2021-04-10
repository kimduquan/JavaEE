/**
 * 
 */
package epf.schema;

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
		loadEvent.fire(new epf.schema.PostLoad(entity));
	}
	
	/**
	 * @param entity
	 */
	@PostPersist
	protected void postPersist(final Object entity) {
		persistEvent.fire(new epf.schema.PostPersist(entity));
	}
	
	/**
	 * @param entity
	 */
	@PostRemove
	protected void postRemove(final Object entity) {
		removeEvent.fire(new epf.schema.PostRemove(entity));
	}
	
	/**
	 * @param entity
	 */
	@PostUpdate
	protected void postUpdate(final Object entity) {
		updateEvent.fire(new epf.schema.PostUpdate(entity));
	}
}
