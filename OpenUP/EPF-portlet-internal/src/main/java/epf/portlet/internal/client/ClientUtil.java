package epf.portlet.internal.client;

import java.net.URI;
import java.util.Objects;
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
		Objects.requireNonNull(url, "URI");
		return new Client(application.getClients().poll(url, b -> b), url, application.getClients()::add);
	}
	
}
