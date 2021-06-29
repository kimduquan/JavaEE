/**
 * 
 */
package epf.portlet;

import javax.inject.Inject;
import javax.inject.Named;
import javax.portlet.PortletRequest;
import javax.portlet.annotations.PortletRequestScoped;

/**
 * @author PC
 *
 */
@PortletRequestScoped
public class RequestUtil {
	
	/**
	 * 
	 */
	@Inject @Named(Bridge.PORTLET_REQUEST)
	private transient PortletRequest request;
	
	/**
	 * @return
	 */
	public PortletRequest getRequest() {
		return request;
	}
	
	/**
	 * @return
	 */
	public boolean isRenderPhase() {
		return PortletRequest.RENDER_PHASE.equals(request.getAttribute(PortletRequest.LIFECYCLE_PHASE));
	}
	
	/**
	 * @return
	 */
	public boolean isActionPhase() {
		return PortletRequest.ACTION_PHASE.equals(request.getAttribute(PortletRequest.LIFECYCLE_PHASE));
	}
}
