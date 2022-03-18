package epf.security.util;

/**
 * @author PC
 *
 */
public interface JdbcUtil {

	/**
	 * @param url
	 * @param tenant
	 * @return
	 */
	static String formatTenantUrl(final String url, final String tenant) {
		return url + "SCHEMA=" + tenant;
	}
}
