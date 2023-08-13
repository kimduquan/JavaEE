package epf.cache.client.util;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Map;
import java.util.Set;
import javax.cache.Cache;

/**
 * 
 */
public class RemoteCacheImpl<K, V> extends UnicastRemoteObject implements RemoteCache {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private transient final Cache<K, V> cache;
	
	/**
	 * @param cache
	 * @throws RemoteException
	 */
	public RemoteCacheImpl(final Cache<K, V> cache) throws RemoteException {
		this.cache = cache;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object get(final Object key) throws RemoteException {
		return cache.get((K)key);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<?, ?> getAll(final Set<?> keys) throws RemoteException {
		return cache.getAll((Set<? extends K>) keys);
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean containsKey(final Object key) throws RemoteException {
		return cache.containsKey((K) key);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void put(final Object key, final Object value) throws RemoteException {
		cache.put((K)key,  (V)value);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void putAll(final Map<?, ?> map) throws RemoteException {
		cache.putAll((Map<? extends K, ? extends V>) map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean remove(final Object key) throws RemoteException {
		return cache.remove((K) key);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void removeAll(final Set<?> keys) throws RemoteException {
		cache.removeAll((Set<? extends K>) keys);
	}

	@Override
	public void close() throws RemoteException {
		cache.close();
	}

	@Override
	public boolean isClosed() throws RemoteException {
		return cache.isClosed();
	}

}
