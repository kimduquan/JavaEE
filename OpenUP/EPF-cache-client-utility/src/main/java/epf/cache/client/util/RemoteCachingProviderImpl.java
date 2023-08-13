package epf.cache.client.util;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import javax.cache.spi.CachingProvider;

/**
 * 
 */
public class RemoteCachingProviderImpl extends UnicastRemoteObject implements RemoteCachingProvider {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private transient final CachingProvider provider;
	
	/**
	 * @param provider
	 * @throws RemoteException
	 */
	public RemoteCachingProviderImpl(final CachingProvider provider) throws RemoteException {
		this.provider = provider;
	}

	@Override
	public RemoteCacheManager getCacheManager() throws RemoteException {
		return new RemoteCacheManagerImpl(provider.getCacheManager());
	}

}
