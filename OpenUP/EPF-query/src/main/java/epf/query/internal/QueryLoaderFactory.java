package epf.query.internal;

import javax.cache.configuration.Factory;
import javax.cache.integration.CacheLoader;
import javax.enterprise.context.ApplicationScoped;

import epf.query.internal.event.QueryLoad;
import epf.query.internal.util.Loader;

/**
 * 
 */
@ApplicationScoped
public class QueryLoaderFactory implements Factory<CacheLoader<String, Integer>> {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 *
	 */
	private transient Factory<Loader<String, Integer, QueryLoad>> factory;

	@Override
	public CacheLoader<String, Integer> create() {
		return factory.create();
	}

	public void setFactory(final Factory<Loader<String, Integer, QueryLoad>> factory) {
		this.factory = factory;
	}

}
