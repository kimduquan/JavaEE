package epf.cache.persistence;

import javax.cache.configuration.Factory;
import javax.persistence.EntityManager;

/**
 * @author PC
 *
 */
public class QueryCacheLoaderFactory implements Factory<QueryCacheLoader> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	private transient final QueryCacheLoader loader;
	
	/**
	 * @param manager
	 * @param schemaCache
	 */
	public QueryCacheLoaderFactory(final EntityManager manager, final SchemaCache schemaCache) {
		loader = new QueryCacheLoader(manager, schemaCache);
	}

	@Override
	public QueryCacheLoader create() {
		return loader;
	}

}
