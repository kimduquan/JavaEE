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
import epf.cache.persistence.event.EntityLoad;

/**
 * @author PC
 *
 */
@ApplicationScoped
public class EntityCacheLoader implements CacheLoader<String, Object> {
	
	/**
	 *
	 */
	@Inject
	private transient Event<EntityLoad> event;

	@Override
	public Object load(final String key) throws CacheLoaderException {
		try {
			final EntityLoad loadEvent = new EntityLoad();
			final List<String> loadKeys = Arrays.asList(key);
			loadEvent.setKeys(loadKeys);
			return event.fireAsync(loadEvent).toCompletableFuture().get().getEntries().get(key);
		}
		catch (Exception e) {
			throw new CacheLoaderException(e);
		}
	}

	@Override
	public Map<String, Object> loadAll(final Iterable<? extends String> keys) throws CacheLoaderException {
		try {
			final EntityLoad loadEvent = new EntityLoad();
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
