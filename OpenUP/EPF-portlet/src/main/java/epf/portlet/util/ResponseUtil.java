/**
 * 
 */
package epf.portlet.util;

import javax.inject.Inject;
import javax.inject.Named;
import javax.portlet.PortletResponse;
import javax.portlet.annotations.PortletRequestScoped;

/**
 * @author PC
 *
 */
@PortletRequestScoped
public class ResponseUtil {

	/**
	 * 
	 */
	@Inject @Named(Bridge.PORTLET_RESPONSE)
	private transient PortletResponse response;

	/**
	 * @return the response
	 */
	public PortletResponse getResponse() {
		return response;
	}
	
}
