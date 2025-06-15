package epf.management.util;

import org.eclipse.microprofile.jwt.JsonWebToken;
import epf.naming.Naming;

public interface TenantUtil {

	static String getTenantId(final JsonWebToken jwt) {
		return jwt.getClaim(Naming.Management.TENANT);
	}
	
	static String getDefaultTenantId() {
		return "";
	}
}
