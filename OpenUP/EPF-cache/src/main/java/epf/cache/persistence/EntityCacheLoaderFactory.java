package epf.cache.persistence;

import javax.cache.configuration.Factory;
import javax.persistence.EntityManagerFactory;
import epf.schema.utility.SchemaUtil;

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
	 * @param factory
	 * @param schemaUtil
	 */
	public EntityCacheLoaderFactory(final EntityManagerFactory factory, final SchemaUtil schemaUtil) {
		loader = new EntityCacheLoader(factory, schemaUtil);
	}

	@Override
	public EntityCacheLoader create() {
		return loader;
	}

}
