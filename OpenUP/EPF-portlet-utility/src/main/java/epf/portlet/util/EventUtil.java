/**
 * 
 */
package epf.portlet.util;

import java.io.Serializable;
import javax.inject.Inject;
import javax.inject.Named;
import javax.portlet.PortletSession;
import javax.portlet.StateAwareResponse;
import javax.portlet.annotations.PortletRequestScoped;
import javax.xml.namespace.QName;
import epf.portlet.naming.Naming;

/**
 * @author PC
 *
 */
@PortletRequestScoped
public class EventUtil {
	
	/**
	 * 
	 */
	@Inject @Named(Naming.Bridge.STATE_AWARE_RESPONSE)
	private transient StateAwareResponse state;
	
	/**
	 * 
	 */
	@Inject @Named(Naming.Bridge.PORTLET_SESSION)
	private transient PortletSession portletSession;
	
	/**
	 * @param qname
	 * @param value
	 */
	public void setEvent(final QName qname, final Serializable value) {
		state.setEvent(qname, value);
	}
	
	@SuppressWarnings("unchecked")
	public <T extends Serializable> T getEvent(final QName qname) {
		return (T) portletSession.getAttribute(qname.toString());
	}
}
