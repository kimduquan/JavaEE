package epf.persistence.remote;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.sql.Savepoint;

public interface RemoteSavepoint extends Savepoint, Remote {

	int _getSavepointId() throws RemoteException, SQLException;

    String _getSavepointName() throws RemoteException, SQLException;
}
