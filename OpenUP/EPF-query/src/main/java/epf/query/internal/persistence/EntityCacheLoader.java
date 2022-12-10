package epf.query.internal.persistence;

import epf.cache.util.Loader;
import epf.query.internal.event.EntityLoad;
import epf.util.event.EventEmitter;
import epf.util.event.EventQueue;

/**
 * @author PC
 *
 */
public class EntityCacheLoader extends Loader<String, Object, EntityLoad> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	private transient static EventQueue<EntityLoad> eventQueue;

	/**
	 * 
	 */
	public EntityCacheLoader() {
		super(new EventEmitter<EntityLoad>(eventQueue), EntityLoad::new);
	}

	public static void setEventQueue(final EventQueue<EntityLoad> eventQueue) {
		EntityCacheLoader.eventQueue = eventQueue;
	}
}
