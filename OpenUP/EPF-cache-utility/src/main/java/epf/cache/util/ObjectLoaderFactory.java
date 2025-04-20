package epf.cache.util;

import javax.cache.integration.CacheLoader;

public class ObjectLoaderFactory extends LoaderFactory<String, Object> {

	private static final long serialVersionUID = 1L;

	public ObjectLoaderFactory(final CacheLoader<String, Object> loader) {
		super(loader);
	}

}
