package epf.persistence.remote;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RemoteObject<T, V> extends UnicastRemoteObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected final T object;
	protected final V _object;

	protected RemoteObject(final T object, final V _object) throws RemoteException {
		super();
		this.object = object;
		this._object = _object;
	}
}
