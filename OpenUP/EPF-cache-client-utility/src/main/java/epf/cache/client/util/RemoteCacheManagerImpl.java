package epf.cache.client.util;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import javax.cache.Cache;
import javax.cache.CacheManager;

/**
 * 
 */
public class RemoteCacheManagerImpl extends UnicastRemoteObject implements RemoteCacheManager {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private transient final CacheManager cacheManager;
	
	/**
	 * @param cacheManager
	 * @throws RemoteException
	 */
	public RemoteCacheManagerImpl(final CacheManager cacheManager) throws RemoteException {
		this.cacheManager = cacheManager;
	}

	@Override
	public RemoteCache getCache(final String cacheName) throws RemoteException {
		final Cache<?, ?> cache = cacheManager.getCache(cacheName);
		if(cache == null) {
			return null;
		}
		return new RemoteCacheImpl<>(cache);
	}

}
