package epf.persistence.remote;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteSQLType extends Remote {
	String getName() throws RemoteException;
	String getVendor() throws RemoteException;
	Integer getVendorTypeNumber() throws RemoteException;
}
