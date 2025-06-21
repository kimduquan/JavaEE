package epf.management.util;

import org.eclipse.microprofile.jwt.JsonWebToken;
import epf.naming.Naming;

public class TenantUtil {
	
	private static final ThreadLocal<String> currentTenant = new ThreadLocal<>();

	public static String getTenantId(final JsonWebToken jwt) {
		return jwt.getClaim(Naming.Management.TENANT);
	}
	
	public static String getDefaultTenantId() {
		return "";
	}
	
	public static void setTenantId(final String tenantId) {
		currentTenant.set(tenantId);
	}
	
	public static String getTenantId() {
		return currentTenant.get();
	}
}
