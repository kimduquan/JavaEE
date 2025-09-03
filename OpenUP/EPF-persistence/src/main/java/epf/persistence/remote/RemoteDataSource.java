package epf.persistence.remote;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;

public interface RemoteDataSource extends RemoteCommonDataSource, RemoteWrapper, DataSource {
	Connection _getConnection() throws RemoteException, SQLException;
	Connection _getConnection(final String username, final String password) throws RemoteException, SQLException;
}
