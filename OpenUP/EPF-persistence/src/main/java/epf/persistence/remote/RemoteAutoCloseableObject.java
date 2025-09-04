package epf.persistence.remote;

import java.rmi.RemoteException;

public class RemoteAutoCloseableObject<U extends AutoCloseable, V extends RemoteAutoCloseable> extends RemoteObject<U, V> implements RemoteAutoCloseable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RemoteAutoCloseableObject(final U autoCloseable, final V _autoCloseable) throws RemoteException {
		super(autoCloseable, _autoCloseable);
	}

	@Override
	public void close() throws Exception {
		_object._close();
	}

	@Override
	public void _close() throws RemoteException {
		try {
			object.close();
		} 
		catch (Exception e) {
			throw new RemoteException();
		}
	}
}
