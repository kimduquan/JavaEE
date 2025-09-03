package epf.persistence.remote;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;

public class RemoteDataSourceObject extends RemoteCommonDataSourceObject implements RemoteDataSource {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final DataSource dataSource;
	private final RemoteDataSource _dataSource;
	private final RemoteWrapper wrapper;

	public RemoteDataSourceObject(final DataSource dataSource, final RemoteDataSource _dataSource) throws RemoteException {
		super(dataSource, _dataSource);
		this.dataSource = dataSource;
		this._dataSource = _dataSource;
		wrapper = new RemoteWrapperObject(dataSource, _dataSource);
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
	public Connection _getConnection() throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Connection _getConnection(final String username, final String password) throws RemoteException, SQLException {
		return dataSource.getConnection();
	}
}
