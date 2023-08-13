package epf.cache.client.util;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * 
 */
public interface RemoteCachingProvider extends Remote {

	/**
	 * @return
	 * @throws RemoteException
	 */
	RemoteCacheManager getCacheManager() throws RemoteException;
}
