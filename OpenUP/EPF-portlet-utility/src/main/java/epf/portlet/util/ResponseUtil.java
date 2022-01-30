/**
 * 
 */
package epf.portlet.util;

import javax.inject.Inject;
import javax.inject.Named;
import javax.portlet.PortletResponse;
import javax.portlet.annotations.PortletRequestScoped;
import epf.portlet.naming.Naming;

/**
 * @author PC
 *
 */
@PortletRequestScoped
public class ResponseUtil {

	/**
	 * 
	 */
	@Inject @Named(Naming.Bridge.PORTLET_RESPONSE)
	private transient PortletResponse response;

	/**
	 * @return the response
	 */
	public PortletResponse getResponse() {
		return response;
	}
	
}
