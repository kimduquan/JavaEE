/**
 * 
 */
package epf.portlet.util;

import javax.inject.Inject;
import javax.inject.Named;
import javax.portlet.PortletSession;
import javax.portlet.annotations.PortletRequestScoped;
import epf.portlet.naming.Naming;

/**
 * @author PC
 *
 */
@PortletRequestScoped
public class SessionUtil {

	/**
	 * 
	 */
	@Inject @Named(Naming.Bridge.PORTLET_SESSION)
	private transient PortletSession portletSession;
	
	/**
	 * @param name
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T extends Object> T getAttribute(final String name) {
		return (T) portletSession.getAttribute(name, PortletSession.APPLICATION_SCOPE);
	}
	
	/**
	 * @param name
	 * @param value
	 */
	public void setAttribute(final String name, final Object value) {
		portletSession.setAttribute(name, value, PortletSession.APPLICATION_SCOPE);
	}
	
	/**
	 * @param <T>
	 * @param name
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T extends Object> T getPortletAttribute(final Class<?> cls, final String name) {
		return (T) portletSession.getAttribute(cls.getName() + "." + name, PortletSession.PORTLET_SCOPE);
	}
	
	/**
	 * @param name
	 * @param value
	 */
	public void setPortletAttribute(final Class<?> cls, final String name, final Object value) {
		portletSession.setAttribute(cls.getName() + "." + name, value, PortletSession.PORTLET_SCOPE);
	}
}
