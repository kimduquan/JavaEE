package epf.persistence.remote;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.sql.Wrapper;

public class RemoteWrapperObject<U extends Wrapper, V extends RemoteWrapper> extends RemoteObject<U, V> implements RemoteWrapper {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RemoteWrapperObject(final U wrapper, final V _wrapper) throws RemoteException {
		super(wrapper, _wrapper);
	}

	@Override
	public <T> T unwrap(final Class<T> iface) throws SQLException {
		try {
			return _object._unwrap(iface);
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public boolean isWrapperFor(final Class<?> iface) throws SQLException {
		try {
			return _object._isWrapperFor(iface);
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public <T> T _unwrap(final Class<T> iface) throws RemoteException, SQLException {
		return object.unwrap(iface);
	}

	@Override
	public boolean _isWrapperFor(final Class<?> iface) throws RemoteException, SQLException {
		return object.isWrapperFor(iface);
	}
}
