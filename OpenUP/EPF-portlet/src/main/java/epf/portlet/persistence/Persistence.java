/**
 * 
 */
package epf.portlet.persistence;

import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import epf.portlet.Event;
import epf.portlet.EventHandler;
import epf.portlet.Portlet;
import epf.portlet.Request;
import epf.portlet.security.Principal;

/**
 * @author PC
 *
 */
@RequestScoped
@Named(Portlet.PERSISTENCE)
public class Persistence implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	@Inject
	private transient Request request;
	
	/**
	 * @return
	 */
	public String getPrincipalName() {
		String name = "";
		final Principal principal = EventHandler.getValue(Event.EPF_SECURITY_PRINCIPAL, request.getRequest());
		if(principal != null) {
			name = principal.getName();
		}
		return name;
	}
}
