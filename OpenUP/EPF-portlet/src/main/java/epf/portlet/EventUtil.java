/**
 * 
 */
package epf.portlet;

import java.io.Serializable;
import javax.inject.Inject;
import javax.inject.Named;
import javax.portlet.PortletSession;
import javax.portlet.annotations.PortletRequestScoped;
import javax.xml.namespace.QName;

/**
 * @author PC
 *
 */
@PortletRequestScoped
public class EventUtil {
	
	/**
	 * 
	 */
	@Inject
	private transient ResponseUtil responseUtil;
	
	/**
	 * 
	 */
	@Inject @Named(Bridge.PORTLET_SESSION)
	private transient PortletSession portletSession;
	
	/**
	 * @param qname
	 * @param value
	 */
	public void setEvent(final QName qname, final Serializable value) {
		responseUtil.setEvent(qname, value);
	}
	
	@SuppressWarnings("unchecked")
	public <T extends Serializable> T getEvent(final QName qname) {
		return (T) portletSession.getAttribute(qname.toString());
	}
}
