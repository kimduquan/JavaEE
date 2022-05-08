package epf.cache.persistence;

import javax.cache.configuration.Factory;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

/**
 * @author PC
 *
 */
@ApplicationScoped
public class QueryCacheLoaderFactory implements Factory<QueryCacheLoader> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	@Inject
	private transient QueryCacheLoader loader;

	@Override
	public QueryCacheLoader create() {
		return loader;
	}

}
