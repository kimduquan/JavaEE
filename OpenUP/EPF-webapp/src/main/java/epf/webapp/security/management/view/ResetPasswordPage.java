package epf.webapp.security.management.view;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;
import epf.security.view.ResetPasswordView;
import epf.webapp.naming.Naming;

/**
 * 
 */
@ViewScoped
@Named(Naming.Security.RESET_PASSWORD)
public class ResetPasswordPage implements ResetPasswordView, Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	/**
	 *
	 */
	private String email;

	public String getEmail() {
		return email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}
	
	public String reset() throws Exception {
		return "";
	}
}
