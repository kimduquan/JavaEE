package epf.persistence.remote;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.sql.Wrapper;

public interface RemoteWrapper extends Remote, Wrapper {
	<T> T _unwrap(final Class<T> iface) throws RemoteException, SQLException;
	boolean _isWrapperFor(final Class<?> iface) throws RemoteException, SQLException;
}
