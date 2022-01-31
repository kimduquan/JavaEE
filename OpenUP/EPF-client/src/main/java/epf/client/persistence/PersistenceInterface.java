package epf.client.persistence;

import java.io.Serializable;
import java.net.URI;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * @author PC
 *
 */
public interface PersistenceInterface extends Remote {

	/**
	 * @param <T>
	 * @param schema
	 * @param entity
	 * @param object
	 * @return
	 * @throws RemoteException
	 */
	<T extends Serializable> T persist(final String schema, final String entity, final T object) throws RemoteException;
	
	/**
	 * @param <T>
	 * @param schema
	 * @param entity
	 * @param entityId
	 * @param object
	 * @throws RemoteException
	 */
	<T extends Serializable> void merge(final String schema, final String entity, final String entityId, final T object) throws RemoteException;
	
	/**
	 * @param schema
	 * @param entity
	 * @param entityId
	 * @throws RemoteException
	 */
	void remove(final String schema, final String entity, final String entityId) throws RemoteException;
	
	/**
	 * @param <T>
	 * @param schema
	 * @param entity
	 * @param entityId
	 * @return
	 * @throws RemoteException
	 */
	<T extends Serializable> T find(final String schema, final String entity, final String entityId) throws RemoteException;
	
	/**
	 * @param text
	 * @param firstResult
	 * @param maxResults
	 * @return
	 * @throws RemoteException
	 */
	List<URI> search(final String text, final Integer firstResult, final Integer maxResults) throws RemoteException;
}
