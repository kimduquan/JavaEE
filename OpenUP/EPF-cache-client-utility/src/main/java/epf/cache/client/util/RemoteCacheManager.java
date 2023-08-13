package epf.cache.client.util;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * 
 */
public interface RemoteCacheManager extends Remote {

	/**
	 * @param cacheName
	 * @return
	 * @throws RemoteException
	 */
	RemoteCache getCache(final String cacheName) throws RemoteException;
}
