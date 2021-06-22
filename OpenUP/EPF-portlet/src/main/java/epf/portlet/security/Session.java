/**
 * 
 */
package epf.portlet.security;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import epf.portlet.Portlet;
import java.io.Serializable;
import java.time.Instant;

/**
 * @author PC
 *
 */
@SessionScoped
@Named(Portlet.SECURITY_SESSION)
public class Session implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	private Principal principal;
	
	/**
	 * @return
	 */
	public boolean isExpired() {
		return principal != null && principal.getToken() != null && Instant.now().getEpochSecond() > principal.getToken().getExpirationTime();
	}
	
	/**
	 * @return
	 */
	public String getToken() {
		return principal.getToken().getRawToken();
	}

	/**
	 * @return the principal
	 */
	public Principal getPrincipal() {
		return principal;
	}

	/**
	 * @param principal the principal to set
	 */
	public void setPrincipal(final Principal principal) {
		this.principal = principal;
	}
}
