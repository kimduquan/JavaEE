package epf.cache.persistence;

import java.util.Optional;
import javax.cache.Cache;
import epf.schema.utility.EntityEvent;
import epf.schema.utility.PostPersist;
import epf.schema.utility.PostRemove;

/**
 * @author PC
 *
 */
public class QueryCache {
	
	/**
	 * 
	 */
	private transient final SchemaCache schemaCache;
	
	/**
	 * 
	 */
	private transient final Cache<String, Integer> cache;
	
	/**
	 * @param cache
	 * @param schemaCache
	 */
	public QueryCache(final Cache<String, Integer> cache, final SchemaCache schemaCache) {
		this.schemaCache = schemaCache;
		this.cache = cache;
	}

	/**
	 * @param event
	 */
	public void accept(final EntityEvent event) {
		final Optional<QueryKey> queryKey = schemaCache.getQueryKey(event.getEntity().getClass());
		if(queryKey.isPresent()) {
			if(event instanceof PostPersist) {
				final Integer count = cache.get(queryKey.get().toString());
				cache.replace(queryKey.get().toString(), count, count + 1);
			}
			else if(event instanceof PostRemove) {
				final Integer count = cache.get(queryKey.get().toString());
				cache.replace(queryKey.get().toString(), count, count - 1);
			}
		}
	}
	
	/**
	 * @param schema
	 * @param entity
	 * @return
	 */
	public Optional<Integer> countEntity(
			final String schema,
            final String entity
            ) {
		final QueryKey queryKey = schemaCache.getQueryKey(schema, entity);
		return Optional.ofNullable(cache.get(queryKey.toString()));
	}
}
