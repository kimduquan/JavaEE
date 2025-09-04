package epf.persistence.remote;

import java.rmi.RemoteException;
import java.sql.SQLException;
import javax.sql.DataSource;

public interface RemoteDataSource extends RemoteCommonDataSource, RemoteWrapper, DataSource {
	RemoteConnection _getConnection() throws RemoteException, SQLException;
	RemoteConnection _getConnection(final String username, final String password) throws RemoteException, SQLException;
}
