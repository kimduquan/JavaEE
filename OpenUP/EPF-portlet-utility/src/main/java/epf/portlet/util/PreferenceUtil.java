/**
 * 
 */
package epf.portlet.util;

import javax.inject.Inject;
import javax.inject.Named;
import javax.portlet.PortletPreferences;
import javax.portlet.annotations.PortletRequestScoped;
import epf.portlet.naming.Naming;

/**
 * @author PC
 *
 */
@PortletRequestScoped
public class PreferenceUtil {

	/**
	 * 
	 */
	@Inject @Named(Naming.Bridge.PORTLET_PREFERENCES)
	private transient PortletPreferences preferences;
	
	/**
	 * @param name
	 * @param def
	 * @return
	 */
	public String getValue(final String name, final String def) {
		return preferences.getValue(name, def);
	}
}
