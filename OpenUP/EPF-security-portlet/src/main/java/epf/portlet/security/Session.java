package epf.portlet.security;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import epf.portlet.naming.Naming;
import epf.security.schema.Token;
import java.io.Serializable;

/**
 * @author PC
 *
 */
@SessionScoped
@Named(Naming.Security.SECURITY_SESSION)
public class Session implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	private Token token;

	public Token getToken() {
		return token;
	}

	public void setToken(final Token token) {
		this.token = token;
	}
}
