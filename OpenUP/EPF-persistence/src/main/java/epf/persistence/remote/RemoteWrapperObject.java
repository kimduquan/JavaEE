package epf.persistence.remote;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.sql.Wrapper;

public class RemoteWrapperObject extends UnicastRemoteObject implements RemoteWrapper {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final Wrapper wrapper;
	private final RemoteWrapper _wrapper;

	public RemoteWrapperObject(final Wrapper wrapper, final RemoteWrapper _wrapper) throws RemoteException {
		super();
		this.wrapper = wrapper;
		this._wrapper = _wrapper;
	}

	@Override
	public <T> T unwrap(final Class<T> iface) throws SQLException {
		try {
			return _wrapper._unwrap(iface);
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public boolean isWrapperFor(final Class<?> iface) throws SQLException {
		try {
			return _wrapper._isWrapperFor(iface);
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public <T> T _unwrap(final Class<T> iface) throws RemoteException, SQLException {
		return wrapper.unwrap(iface);
	}

	@Override
	public boolean _isWrapperFor(final Class<?> iface) throws RemoteException, SQLException {
		return wrapper.isWrapperFor(iface);
	}
}
