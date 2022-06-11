package epf.cache.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import javax.cache.integration.CacheLoader;
import javax.cache.integration.CacheLoaderException;
import epf.util.event.Emitter;

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
	 * @param emitter
	 * @param factory
	 */
	public Loader(final Emitter<E> emitter, final Supplier<E> factory) {
		this.emitter = emitter;
		this.factory = factory;
	}

	@Override
	public V load(final K key) throws CacheLoaderException {
		try {
			if(emitter != null) {
				final E event = factory.get();
				final List<K> loadKeys = Arrays.asList(key);
				event.setKeys(loadKeys);
				return emitter.send(event).toCompletableFuture().get().getEntries().get(key);
			}
			else {
				return null;
			}
		}
		catch (Exception e) {
			throw new CacheLoaderException(e);
		}
	}

	@Override
	public Map<K, V> loadAll(final Iterable<? extends K> keys) throws CacheLoaderException {
		try {
			if(emitter != null) {
				final E event = factory.get();
				final List<K> loadKeys = new ArrayList<>();
				keys.forEach(loadKeys::add);
				event.setKeys(loadKeys);
				return emitter.send(event).toCompletableFuture().get().getEntries();
			}
			else {
				return new HashMap<>();
			}
		}
		catch (Exception e) {
			throw new CacheLoaderException(e);
		}
	}
}
