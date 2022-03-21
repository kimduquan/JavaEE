package epf.webapp.security;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
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

	public boolean isRemember() {
		return remember;
	}

	public void setRemember(final boolean remember) {
		this.remember = remember;
	}
}
