package epf.cache.persistence;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.cache.integration.CacheLoader;
import javax.cache.integration.CacheLoaderException;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import epf.cache.persistence.event.QueryLoad;

/**
 * @author PC
 *
 */
@ApplicationScoped
public class QueryCacheLoader implements CacheLoader<String, Integer> {
	
	/**
	 *
	 */
	@Inject
	private transient Event<QueryLoad> event;

	@Override
	public Integer load(final String key) throws CacheLoaderException {
		try {
			final QueryLoad loadEvent = new QueryLoad();
			final List<String> loadKeys = Arrays.asList(key);
			loadEvent.setKeys(loadKeys);
			return event.fireAsync(loadEvent).toCompletableFuture().get().getEntries().get(key);
		}
		catch (Exception e) {
			throw new CacheLoaderException(e);
		}
	}

	@Override
	public Map<String, Integer> loadAll(final Iterable<? extends String> keys) throws CacheLoaderException {
		try {
			final QueryLoad loadEvent = new QueryLoad();
			final List<String> loadKeys = new ArrayList<>();
			keys.forEach(loadKeys::add);
			loadEvent.setKeys(loadKeys);
			return event.fireAsync(loadEvent).toCompletableFuture().get().getEntries();
		}
		catch (Exception e) {
			throw new CacheLoaderException(e);
		}
	}
}
