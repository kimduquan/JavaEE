package epf.cache.util;

import java.util.function.Supplier;
import epf.util.concurrent.ext.Emitter;

/**
 * 
 */
public class ObjectLoader extends Loader<String, Object, ObjectLoad> {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param tenant
	 * @param emitter
	 * @param factory
	 */
	public ObjectLoader(final String tenant, final Emitter<ObjectLoad> emitter, final Supplier<ObjectLoad> factory) {
		super(tenant, emitter, factory);
	}

}
