package epf.security.webapp.util;

import javax.enterprise.context.RequestScoped;
import javax.faces.push.Push;
import javax.faces.push.PushContext;
import javax.inject.Inject;
import javax.inject.Named;
import epf.security.webapp.Naming;
import epf.security.webapp.Session;

/**
 * @author PC
 *
 */
@RequestScoped
@Named(Naming.SESSION_UTILITY)
public class SessionUtil {

	/**
	 * 
	 */
	@Inject 
	@Push(channel = epf.naming.Naming.SECURITY)
	private transient PushContext push;
	
	/**
	 * 
	 */
	@Inject
	private transient Session session;
	
	/**
	 * 
	 */
	public void sendToken() {
		push.send(session.getPrincipal().getToken(), session.getPrincipal().getName());
	}
}
