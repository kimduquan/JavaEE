package epf.persistence.security;

import java.rmi.RemoteException;
import javax.enterprise.inject.spi.CDI;
import epf.security.client.SecurityInterface;
import epf.security.schema.Token;

/**
 * @author PC
 *
 */
public class SecurityProxy implements SecurityInterface {

	@Override
	public Token login(final String username, final String passwordHash, final String url) throws RemoteException {
		return CDI.current().select(SecurityInterface.class).get().login(username, passwordHash, url);
	}

	@Override
	public Token authenticate(final Token token) throws RemoteException {
		return CDI.current().select(SecurityInterface.class).get().authenticate(token);
	}

	@Override
	public String logout(final Token token) throws RemoteException {
		return CDI.current().select(SecurityInterface.class).get().logout(token);
	}

}
