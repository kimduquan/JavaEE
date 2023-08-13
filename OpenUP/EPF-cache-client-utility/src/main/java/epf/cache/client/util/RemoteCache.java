package epf.cache.client.util;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;
import java.util.Set;

/**
 * 
 */
public interface RemoteCache extends Remote {

	/**
	 * @param key
	 * @return
	 * @throws RemoteException
	 */
	Object get(final Object key) throws RemoteException;
	
	/**
	 * @param keys
	 * @return
	 * @throws RemoteException
	 */
	Map<?, ?> getAll(final Set<?> keys) throws RemoteException;
	
	/**
	 * @param key
	 * @return
	 * @throws RemoteException
	 */
	boolean containsKey(final Object key) throws RemoteException;
	
	/**
	 * @param key
	 * @param value
	 * @throws RemoteException
	 */
	void put(final Object key, final Object value) throws RemoteException;
	
	/**
	 * @param map
	 * @throws RemoteException
	 */
	void putAll(final Map<?, ?> map) throws RemoteException;
	
	/**
	 * @param key
	 * @return
	 * @throws RemoteException
	 */
	boolean remove(final Object key) throws RemoteException;
	
	/**
	 * @param keys
	 * @throws RemoteException
	 */
	void removeAll(final Set<?> keys) throws RemoteException;
	
	/**
	 * @throws RemoteException
	 */
	void close() throws RemoteException;
	
    /**
     * @return
     * @throws RemoteException
     */
    boolean isClosed() throws RemoteException;
}
