package epf.cache.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.cache.integration.CacheLoader;
import javax.cache.integration.CacheLoaderException;

public class EmptyLoader<K, V> implements CacheLoader<K, V> {
	
	private transient final Map<K, V> empty = new ConcurrentHashMap<>();

	@Override
	public V load(final K key) throws CacheLoaderException {
		return null;
	}

	@Override
	public Map<K, V> loadAll(final Iterable<? extends K> keys) throws CacheLoaderException {
		return empty;
	}

}
