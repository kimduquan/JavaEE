package epf.webapp.security;

import java.io.Serializable;
import jakarta.enterprise.context.SessionScoped;

@SessionScoped
public class AuthParams implements Serializable {

	private static final long serialVersionUID = 1L;

	private boolean rememberMe;

	public boolean isRememberMe() {
		return rememberMe;
	}

	public void setRememberMe(final boolean rememberMe) {
		this.rememberMe = rememberMe;
	}
}
