package epf.query.cache;

import javax.cache.configuration.Factory;
import javax.cache.integration.CacheLoader;
import javax.enterprise.context.ApplicationScoped;
import epf.query.cache.event.EntityLoad;
import epf.query.cache.util.Loader;

/**
 * 
 */
@ApplicationScoped
public class EntityLoaderFactory implements Factory<CacheLoader<String, Object>> {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 *
	 */
	private transient Factory<Loader<String, Object, EntityLoad>> factory;

	@Override
	public CacheLoader<String, Object> create() {
		return factory.create();
	}

	public void setFactory(final Factory<Loader<String, Object, EntityLoad>> factory) {
		this.factory = factory;
	}

}
