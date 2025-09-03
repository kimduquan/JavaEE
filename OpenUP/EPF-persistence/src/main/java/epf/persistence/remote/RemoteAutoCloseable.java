package epf.persistence.remote;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteAutoCloseable extends AutoCloseable, Remote {

	void close() throws Exception;
	void _close() throws RemoteException;
}
