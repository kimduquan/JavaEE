package epf.security.util;

/**
 * @author PC
 *
 */
public interface JdbcUtil {

	/**
	 * @param url
	 * @param ternant
	 * @return
	 */
	static String formatTernantUrl(final String url, final String ternant) {
		return url + "SCHEMA=" + ternant;
	}
}
