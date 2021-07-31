/**
 * 
 */
package epf.portlet;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

/**
 * @author PC
 *
 */
@ApplicationScoped
@Named("string")
public class StringUtil {

	/**
	 * @param format
	 * @param objects
	 * @return
	 */
	public String format(final String format, final Object...objects) {
		return String.format(format, objects);
	}
}
