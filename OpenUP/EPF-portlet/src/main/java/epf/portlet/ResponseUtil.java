/**
 * 
 */
package epf.portlet;

import java.io.Serializable;
import javax.inject.Inject;
import javax.inject.Named;
import javax.portlet.StateAwareResponse;
import javax.portlet.annotations.PortletRequestScoped;
import javax.xml.namespace.QName;

/**
 * @author PC
 *
 */
@PortletRequestScoped
public class ResponseUtil {

	/**
	 * 
	 */
	@Inject @Named(Bridge.STATE_AWARE_RESPONSE)
	private transient StateAwareResponse state;
	
	/**
	 * @param qname
	 * @param value
	 */
	public void setEvent(final QName qname, final Serializable value) {
		state.setEvent(qname, value);
	}
}
