/**
 * 
 */
package epf.portlet.util.client;

import java.net.URI;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import epf.client.util.Client;

/**
 * @author PC
 *
 */
@RequestScoped
public class ClientUtil {

	/**
	 * 
	 */
	@Inject
	private transient Application application;
	
	/**
	 * @param service
	 * @return
	 */
	public Client newClient(final URI url) {
		return new Client(application.getClients(), url, b -> b);
	}
	
}
