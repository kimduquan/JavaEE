package epf.query.internal.persistence;

import epf.cache.util.Loader;
import epf.query.internal.event.QueryLoad;
import epf.util.event.EventEmitter;
import epf.util.event.EventQueue;

/**
 * @author PC
 *
 */
public class QueryCacheLoader extends Loader<String, Integer, QueryLoad> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	private transient static EventQueue<QueryLoad> eventQueue;

	public QueryCacheLoader() {
		super(new EventEmitter<QueryLoad>(eventQueue), QueryLoad::new);
	}

	public static void setEventQueue(final EventQueue<QueryLoad> eventQueue) {
		QueryCacheLoader.eventQueue = eventQueue;
	}

}
