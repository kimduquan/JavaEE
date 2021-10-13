/**
 * 
 */
package epf.portlet.util;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

/**
 * @author PC
 *
 */
@ApplicationScoped
@Named("style")
public class StyleUtil {

	/**
	 * @param name
	 * @return
	 */
	public String css(final String name) {
		return name.replace('.', ' ');
	}
}
