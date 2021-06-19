/**
 * 
 */
package epf.portlet;

import javax.inject.Inject;
import javax.inject.Named;
import javax.portlet.PortletPreferences;
import javax.portlet.annotations.PortletRequestScoped;

/**
 * @author PC
 *
 */
@PortletRequestScoped
public class Request {

	/**
	 * 
	 */
	@Inject @Named("portletPreferences")
	private transient PortletPreferences preferences;
	
	/**
	 * @return
	 */
	public PortletPreferences getPreferences() {
		return preferences;
	}
}
