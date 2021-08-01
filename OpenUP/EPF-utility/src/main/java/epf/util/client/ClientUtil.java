/**
 * 
 */
package epf.util.client;

import java.net.URI;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

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
	protected transient ClientQueue clients;
	
	/**
	 * @param url
	 * @return
	 */
	public Client newClient(final URI url) {
		return new Client(clients, url, b -> b);
	}
}
