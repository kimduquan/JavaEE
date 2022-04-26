package epf.cache.persistence;

import javax.cache.configuration.Factory;
import javax.persistence.EntityManager;

/**
 * @author PC
 *
 */
public class EntityCacheLoaderFactory implements Factory<EntityCacheLoader> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	private transient final EntityCacheLoader loader;
	
	/**
	 * @param manager
	 * @param schemaCache
	 */
	public EntityCacheLoaderFactory(final EntityManager manager, final SchemaCache schemaCache) {
		loader = new EntityCacheLoader(manager, schemaCache);
	}

	@Override
	public EntityCacheLoader create() {
		return loader;
	}

}
