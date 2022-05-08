package epf.cache.persistence;

import javax.cache.configuration.Factory;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

/**
 * @author PC
 *
 */
@ApplicationScoped
public class EntityCacheLoaderFactory implements Factory<EntityCacheLoader> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 *
	 */
	@Inject
	private transient EntityCacheLoader loader;

	@Override
	public EntityCacheLoader create() {
		return loader;
	}
}
