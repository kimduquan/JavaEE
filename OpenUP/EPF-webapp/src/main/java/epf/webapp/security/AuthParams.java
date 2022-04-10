package epf.webapp.security;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;

/**
 * @author PC
 *
 */
@SessionScoped
public class AuthParams implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private boolean rememberMe;

	public boolean isRememberMe() {
		return rememberMe;
	}

	public void setRememberMe(final boolean rememberMe) {
		this.rememberMe = rememberMe;
	}
}
