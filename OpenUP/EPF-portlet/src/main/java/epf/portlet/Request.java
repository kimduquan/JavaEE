/**
 * 
 */
package epf.portlet;

import javax.inject.Inject;
import javax.inject.Named;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
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
	 * 
	 */
	@Inject @Named("portletRequest")
	private transient PortletRequest request;
	
	/**
	 * @return
	 */
	public PortletPreferences getPreferences() {
		return preferences;
	}
	
	/**
	 * @return
	 */
	public PortletRequest getRequest() {
		return request;
	}
}
