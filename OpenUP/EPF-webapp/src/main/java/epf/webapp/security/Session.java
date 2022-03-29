package epf.webapp.security;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import epf.security.auth.core.UserInfo;
import epf.webapp.naming.Naming;

/**
 * @author PC
 *
 */
@SessionScoped
@Named(Naming.Security.SESSION)
public class Session implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	private boolean remember;
	
	/**
	 * 
	 */
	private UserInfo userInfo;

	public boolean isRemember() {
		return remember;
	}

	public void setRemember(final boolean remember) {
		this.remember = remember;
	}

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(final UserInfo userInfo) {
		this.userInfo = userInfo;
	}
}
