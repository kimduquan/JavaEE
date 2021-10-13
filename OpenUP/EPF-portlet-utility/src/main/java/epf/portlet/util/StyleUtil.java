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
@Named(Naming.STYLE)
public class StyleUtil {

	/**
	 * @param name
	 * @return
	 */
	public String css(final String name) {
		return name.replace('.', ' ');
	}
}
