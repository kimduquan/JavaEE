/**
 * 
 */
package epf.portlet.util;

import javax.faces.context.FacesContext;
import javax.portlet.Event;
import javax.portlet.PortletSession;
import javax.portlet.faces.BridgeEventHandler;
import javax.portlet.faces.event.EventNavigationResult;

/**
 * @author PC
 *
 */
public class EventHandler implements BridgeEventHandler {
	
	@Override
	public EventNavigationResult handleEvent(final FacesContext facesContext, final Event event) {
		final PortletSession session = (PortletSession) facesContext.getExternalContext().getSession(false);
		session.setAttribute(event.getQName().toString(), event.getValue());
		final EventNavigationResult result = new EventNavigationResult();
		return result;
	}
}
