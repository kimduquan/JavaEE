package epf.cache.persistence;

import java.util.function.Supplier;
import javax.cache.configuration.Factory;
import epf.cache.persistence.event.CacheLoad;
import epf.cache.util.EventEmitter;

/**
 * 
 */
public class LoaderFactory<K, V, E extends CacheLoad<K, V>> implements Factory<Loader<K, V, E>> {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 *
	 */
	private transient final Loader<K, V, E> loader;
	
	/**
	 * @param emitter
	 * @param factory
	 */
	public LoaderFactory(final EventEmitter<E> emitter, final Supplier<E> factory) {
		this.loader = new Loader<K, V, E>(emitter, factory);
	}

	@Override
	public Loader<K, V, E> create() {
		return loader;
	}
}
