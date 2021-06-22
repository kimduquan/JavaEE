/**
 * 
 */
package epf.portlet;

import javax.inject.Inject;
import javax.inject.Named;
import javax.portlet.StateAwareResponse;
import javax.portlet.annotations.PortletRequestScoped;

/**
 * @author PC
 *
 */
@PortletRequestScoped
public class Response {

	/**
	 * 
	 */
	@Inject @Named("stateAwareResponse")
	private transient StateAwareResponse state;

	/**
	 * @return the state
	 */
	public StateAwareResponse getState() {
		return state;
	}
}
