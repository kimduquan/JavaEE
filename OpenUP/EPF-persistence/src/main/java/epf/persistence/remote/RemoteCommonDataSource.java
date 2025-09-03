package epf.persistence.remote;

import java.io.PrintWriter;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;
import javax.sql.CommonDataSource;

public interface RemoteCommonDataSource extends CommonDataSource, Remote {
	PrintWriter _getLogWriter() throws RemoteException, SQLException;
	void _setLogWriter(final PrintWriter out) throws RemoteException, SQLException;
	void _setLoginTimeout(final int seconds) throws RemoteException, SQLException;
	int _getLoginTimeout() throws RemoteException, SQLException;
	Logger _getParentLogger() throws RemoteException, SQLFeatureNotSupportedException;
}
