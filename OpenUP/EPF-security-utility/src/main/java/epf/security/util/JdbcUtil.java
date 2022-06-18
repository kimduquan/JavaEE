package epf.security.util;

/**
 * @author PC
 *
 */
public interface JdbcUtil {

	/**
	 * @param jdbcUrlFormat
	 * @param schema
	 * @param tenant
	 * @return
	 */
	static String formatJdbcUrl(final String jdbcUrlFormat, final String schema, final String tenant, final String separator) {
		return String.format(jdbcUrlFormat, schema, separator, tenant);
	}
}
