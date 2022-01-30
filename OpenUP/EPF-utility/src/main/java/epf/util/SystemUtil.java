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
	
	/**
	 * @param name
	 * @return
	 */
	static String getString(final String name) {
		String value = getenv(name);
		if(value == null) {
			value = System.getProperty(name);
		}
		return value;
	}
}
