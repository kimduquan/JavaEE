package epf.cache.persistence;

import javax.cache.configuration.Factory;
import javax.persistence.EntityManagerFactory;
import epf.schema.utility.SchemaUtil;

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
	 * @param factory
	 * @param schemaUtil
	 */
	public QueryCacheLoaderFactory(final EntityManagerFactory factory, final SchemaUtil schemaUtil) {
		loader = new QueryCacheLoader(factory, schemaUtil);
	}

	@Override
	public QueryCacheLoader create() {
		return loader;
	}

}
