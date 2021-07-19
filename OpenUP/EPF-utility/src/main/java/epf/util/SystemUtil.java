/**
 * 
 */
package epf.util;

/**
 * @author PC
 *
 */
public interface SystemUtil {

	/**
	 * @param name
	 * @return
	 */
	static String getenv(final String name) {
		return System.getenv(name.replace('.', '_'));
	}
}
