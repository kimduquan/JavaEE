/**
 * 
 */
package epf.portlet.security;

import javax.enterprise.context.RequestScoped;
import epf.client.security.Credential;
import epf.portlet.Portlet;
import javax.inject.Named;
import java.io.Serializable;

/**
 * @author PC
 *
 */
@Named(Portlet.SECURITY)
@RequestScoped
public class Security implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	private final Credential credential = new Credential();
	
//	@Inject 
//	private transient Session session;
//	
//	@Inject 
//	private transient ClientQueue clients;
//  
//	@Inject 
//	private transient LocateRegistry registry;
	 

	public Credential getCredential() {
		return credential;
	}
	
	/**
	 * @return
	 */
	public String login() {
		/*final URI securityUrl = registry.lookup("security");
		final String passwordHash = PasswordHelper.hash(credential.getCaller(), credential.getPassword());
		try(Client client = new Client(clients, securityUrl, b -> b)){
			final String token = epf.client.security.Security.login(
					client, 
					null, 
					credential.getCaller(), 
					passwordHash, 
					credential.getUrl()
					);
			session.setToken(token);
		}*/
		return "session";
	}
}
