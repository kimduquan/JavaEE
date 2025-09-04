package epf.persistence.remote;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;

public class RemoteDataSourceObject<U extends DataSource, V extends RemoteDataSource> extends RemoteCommonDataSourceObject<U, V> implements RemoteDataSource {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final RemoteWrapper wrapper;

	public RemoteDataSourceObject(final U dataSource, final V _dataSource) throws RemoteException {
		super(dataSource, _dataSource);
		wrapper = new RemoteWrapperObject<U, V>(dataSource, _dataSource);
	}

	@Override
	public <T> T _unwrap(final Class<T> iface) throws RemoteException, SQLException {
		return wrapper._unwrap(iface);
	}

	@Override
	public boolean _isWrapperFor(final Class<?> iface) throws RemoteException, SQLException {
		return wrapper._isWrapperFor(iface);
	}

	@Override
	public <T> T unwrap(final Class<T> iface) throws SQLException {
		return wrapper.unwrap(iface);
	}

	@Override
	public boolean isWrapperFor(final Class<?> iface) throws SQLException {
		return wrapper.isWrapperFor(iface);
	}

	@Override
	public Connection getConnection() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Connection getConnection(final String username, final String password) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RemoteConnection _getConnection() throws RemoteException, SQLException {
		return new RemoteConnectionObject<>(object.getConnection(), null);
	}

	@Override
	public RemoteConnection _getConnection(final String username, final String password) throws RemoteException, SQLException {
		return new RemoteConnectionObject<>(object.getConnection(username, password), null);
	}
}
