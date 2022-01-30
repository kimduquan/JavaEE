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
	
	/**
	 * @param thisObject
	 * @param object
	 * @param e
	 */
	static void print(final Object thisObject, final Object object, final Throwable e) {
		print(thisObject, object);
		e.printStackTrace();
	}
}
