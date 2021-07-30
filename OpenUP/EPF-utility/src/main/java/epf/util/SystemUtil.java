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
	 * 
	 */
	String OS_NAME = System.getProperty("os.name");

	/**
	 * @param name
	 * @return
	 */
	static String getenv(final String name) {
		return System.getenv(name.replace('.', '_'));
	}
}
