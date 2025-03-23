package epf.auth.model;

import org.springframework.security.core.GrantedAuthority;

public class Role implements GrantedAuthority {

	private static final long serialVersionUID = 1L;
	
	private String authority;

	@Override
	public String getAuthority() {
		return authority;
	}
}
