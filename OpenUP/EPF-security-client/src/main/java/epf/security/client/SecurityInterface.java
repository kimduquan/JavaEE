package epf.security.client;

import java.rmi.Remote;
import java.rmi.RemoteException;
import epf.security.schema.Token;

/**
 * @author PC
 *
 */
public interface SecurityInterface extends Remote {

	/**
	 * @param username
	 * @param passwordHash
	 * @param url
	 * @return
	 * @throws RemoteException
	 */
	Token login(final String username, final String passwordHash, final String url) throws RemoteException;
	
	/**
	 * @param token
	 * @return
	 * @throws RemoteException
	 */
	Token authenticate(final Token token) throws RemoteException;
	
	/**
	 * @param token
	 * @return
	 * @throws RemoteException
	 */
	String logout(final Token token) throws RemoteException;
}
