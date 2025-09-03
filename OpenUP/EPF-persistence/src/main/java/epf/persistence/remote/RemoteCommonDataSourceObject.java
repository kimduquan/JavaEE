package epf.persistence.remote;

import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;
import javax.sql.CommonDataSource;

public class RemoteCommonDataSourceObject extends UnicastRemoteObject implements RemoteCommonDataSource {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final CommonDataSource commonDataSource;
	private final RemoteCommonDataSource _commonDataSource;

	public RemoteCommonDataSourceObject(final CommonDataSource commonDataSource, final RemoteCommonDataSource _commonDataSource) throws RemoteException {
		super();
		this.commonDataSource = commonDataSource;
		this._commonDataSource = _commonDataSource;
	}

	@Override
	public PrintWriter getLogWriter() throws SQLException {
		try {
			return _commonDataSource._getLogWriter();
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public void setLogWriter(final PrintWriter out) throws SQLException {
		try {
			_commonDataSource._setLogWriter(out);
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public void setLoginTimeout(final int seconds) throws SQLException {
		try {
			_commonDataSource._setLoginTimeout(seconds);
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public int getLoginTimeout() throws SQLException {
		try {
			return _commonDataSource._getLoginTimeout();
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		try {
			return _commonDataSource._getParentLogger();
		}
		catch(RemoteException ex) {
			throw new SQLFeatureNotSupportedException();
		}
	}

	@Override
	public PrintWriter _getLogWriter() throws RemoteException, SQLException {
		return commonDataSource.getLogWriter();
	}

	@Override
	public void _setLogWriter(final PrintWriter out) throws RemoteException, SQLException {
		commonDataSource.setLogWriter(out);
	}

	@Override
	public void _setLoginTimeout(final int seconds) throws RemoteException, SQLException {
		commonDataSource.setLoginTimeout(seconds);
	}

	@Override
	public int _getLoginTimeout() throws RemoteException, SQLException {
		return commonDataSource.getLoginTimeout();
	}

	@Override
	public Logger _getParentLogger() throws RemoteException, SQLFeatureNotSupportedException {
		return commonDataSource.getParentLogger();
	}
}
