/**
 * 
 */
package epf.util;

/**
 * @author PC
 *
 */
public interface ObjectUtil {

	/**
	 * @param thisObject
	 * @param object
	 */
	static void print(final Object thisObject, final Object object) {
		System.out.println(String.format("%s/%s", thisObject.getClass().getName(), object));
	}
}
