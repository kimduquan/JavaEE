/**
 * 
 */
package epf.portlet.util;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import epf.portlet.naming.Naming;

/**
 * @author PC
 *
 */
@ApplicationScoped
@Named(Naming.STRING)
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
