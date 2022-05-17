package epf.query.internal.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import javax.cache.integration.CacheLoader;
import javax.cache.integration.CacheLoaderException;

import epf.query.internal.event.CacheLoad;
import epf.util.event.EventEmitter;

/**
 * @param <K>
 * @param <V>
 */
public class Loader<K, V, E extends CacheLoad<K, V>> implements CacheLoader<K, V> {
	
	/**
	 *
	 */
	private transient final EventEmitter<E> emitter;
	
	/**
	 *
	 */
	private transient final Supplier<E> factory;
	
	/**
	 * @param emitter
	 * @param factory
	 */
	public Loader(final EventEmitter<E> emitter, final Supplier<E> factory) {
		this.emitter = emitter;
		this.factory = factory;
	}

	@Override
	public V load(final K key) throws CacheLoaderException {
		try {
			final E event = factory.get();
			final List<K> loadKeys = Arrays.asList(key);
			event.setKeys(loadKeys);
			return emitter.send(event).toCompletableFuture().get().getEntries().get(key);
		}
		catch (Exception e) {
			throw new CacheLoaderException(e);
		}
	}

	@Override
	public Map<K, V> loadAll(final Iterable<? extends K> keys) throws CacheLoaderException {
		try {
			final E event = factory.get();
			final List<K> loadKeys = new ArrayList<>();
			keys.forEach(loadKeys::add);
			event.setKeys(loadKeys);
			return emitter.send(event).toCompletableFuture().get().getEntries();
		}
		catch (Exception e) {
			throw new CacheLoaderException(e);
		}
	}
}
