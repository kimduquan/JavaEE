package epf.persistence.remote;

import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;
import javax.sql.CommonDataSource;

public class RemoteCommonDataSourceObject<U extends CommonDataSource, V extends RemoteCommonDataSource> extends RemoteObject<U, V> implements RemoteCommonDataSource {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RemoteCommonDataSourceObject(final U commonDataSource, final V _commonDataSource) throws RemoteException {
		super(commonDataSource, _commonDataSource);
	}

	@Override
	public PrintWriter getLogWriter() throws SQLException {
		try {
			return _object._getLogWriter();
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public void setLogWriter(final PrintWriter out) throws SQLException {
		try {
			_object._setLogWriter(out);
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public void setLoginTimeout(final int seconds) throws SQLException {
		try {
			_object._setLoginTimeout(seconds);
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public int getLoginTimeout() throws SQLException {
		try {
			return _object._getLoginTimeout();
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		try {
			return _object._getParentLogger();
		}
		catch(RemoteException ex) {
			throw new SQLFeatureNotSupportedException();
		}
	}

	@Override
	public PrintWriter _getLogWriter() throws RemoteException, SQLException {
		return object.getLogWriter();
	}

	@Override
	public void _setLogWriter(final PrintWriter out) throws RemoteException, SQLException {
		object.setLogWriter(out);
	}

	@Override
	public void _setLoginTimeout(final int seconds) throws RemoteException, SQLException {
		object.setLoginTimeout(seconds);
	}

	@Override
	public int _getLoginTimeout() throws RemoteException, SQLException {
		return object.getLoginTimeout();
	}

	@Override
	public Logger _getParentLogger() throws RemoteException, SQLFeatureNotSupportedException {
		return object.getParentLogger();
	}
}
