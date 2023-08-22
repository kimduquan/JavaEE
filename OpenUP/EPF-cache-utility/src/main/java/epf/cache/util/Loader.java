package epf.cache.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;
import javax.cache.integration.CacheLoader;
import javax.cache.integration.CacheLoaderException;
import epf.util.concurrent.Emitter;

/**
 * @param <K>
 * @param <V>
 */
public class Loader<K, V, E extends CacheLoad<K, V>> implements CacheLoader<K, V>, Serializable {
	
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	private transient final Emitter<E> emitter;
	
	/**
	 *
	 */
	private transient final Supplier<E> factory;
	
	/**
	 * 
	 */
	private final String tenant;
	
	/**
	 * @param tenant
	 * @param emitter
	 * @param factory
	 */
	public Loader(final String tenant, final Emitter<E> emitter, final Supplier<E> factory) {
		Objects.requireNonNull(emitter, "Emitter");
		Objects.requireNonNull(factory, "Supplier");
		this.tenant = tenant;
		this.emitter = emitter;
		this.factory = factory;
	}

	@Override
	public V load(final K key) throws CacheLoaderException {
		try {
			final E event = factory.get();
			event.setTenant(tenant);
			final List<K> loadKeys = Arrays.asList(key);
			event.setKeys(loadKeys);
			emitter.send(event);
			return event.getEntries().get(key);
		}
		catch (Exception e) {
			throw new CacheLoaderException(e);
		}
	}

	@Override
	public Map<K, V> loadAll(final Iterable<? extends K> keys) throws CacheLoaderException {
		try {
			final E event = factory.get();
			event.setTenant(tenant);
			final List<K> loadKeys = new ArrayList<>();
			keys.forEach(loadKeys::add);
			event.setKeys(loadKeys);
			emitter.send(event);
			return event.getEntries();
		}
		catch (Exception e) {
			throw new CacheLoaderException(e);
		}
	}
}
