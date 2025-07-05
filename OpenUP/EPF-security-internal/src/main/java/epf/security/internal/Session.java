package epf.security.internal;

import java.util.Map;
import java.util.Set;

public class Session {
    
    private final String name;
    
    private final Set<String> groups;
    
    private final Map<String, Object> claims;

    public Session(final String name, final Set<String> groups, final Map<String, Object> claims) {
		this.name = name;
		this.groups = groups;
		this.claims = claims;
    }

	public Set<String> getGroups() {
		return groups;
	}

	public Map<String, Object> getClaims() {
		return claims;
	}

	public String getName() {
		return name;
	}
}
