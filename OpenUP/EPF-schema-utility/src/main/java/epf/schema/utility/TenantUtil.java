package epf.schema.utility;

/**
 * 
 */
public interface TenantUtil {

	/**
	 * @param schema
	 * @param tenant
	 * @return
	 */
	static String getTenantId(final String schema, final String tenant) {
		String tenantId = null;
		if(schema != null) {
			tenantId = schema;
			if(tenant != null) {
				tenantId = schema + "_" + tenant;
			}
		}
		return tenantId;
	}
}
