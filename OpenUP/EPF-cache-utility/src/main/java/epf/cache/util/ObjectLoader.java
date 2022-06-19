package epf.cache.util;

import java.util.function.Supplier;
import epf.util.event.Emitter;

/**
 * 
 */
public class ObjectLoader extends Loader<String, Object, ObjectLoad> {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param emitter
	 * @param factory
	 */
	public ObjectLoader(final Emitter<ObjectLoad> emitter, final Supplier<ObjectLoad> factory) {
		super(emitter, factory);
	}

}