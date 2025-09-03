package epf.persistence.remote;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RemoteAutoCloseableObject extends UnicastRemoteObject implements RemoteAutoCloseable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final AutoCloseable autoCloseable;
	private final RemoteAutoCloseable _autoCloseable;

	public RemoteAutoCloseableObject(final AutoCloseable autoCloseable, final RemoteAutoCloseable _autoCloseable) throws RemoteException {
		super();
		this.autoCloseable = autoCloseable;
		this._autoCloseable = _autoCloseable;
	}

	@Override
	public void close() throws Exception {
		_autoCloseable._close();
	}

	@Override
	public void _close() throws RemoteException {
		try {
			autoCloseable.close();
		} 
		catch (Exception e) {
			throw new RemoteException();
		}
	}
}
