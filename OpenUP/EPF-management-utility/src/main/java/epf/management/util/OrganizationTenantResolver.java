package epf.management.util;

import org.eclipse.microprofile.jwt.JsonWebToken;

public abstract class OrganizationTenantResolver {
	
	protected abstract JsonWebToken getToken();

	public String getDefaultTenantId() {
		return "";
	}

    public String resolveTenantId() {
    	final JsonWebToken token = getToken();
    	return OrganizationUtil.getOrganizationId(token).orElse("");
    }

    public boolean isRoot(final String tenantId) {
        return tenantId.isEmpty();
    }
}
