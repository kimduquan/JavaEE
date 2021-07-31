/**
 * 
 */
package epf.portlet.registry;

import java.net.URI;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.enterprise.context.ApplicationScoped;
import epf.util.client.Client;

/**
 * @author PC
 *
 */
@ApplicationScoped
public class Registry {
	
	/**
	 * 
	 */
	private final Map<String, URI> remotes = new ConcurrentHashMap<>();
	
	/**
	 * @throws Exception 
	 * 
	 */
	protected void list(final Client client) throws Exception {
		epf.client.registry.Registry.list(client, null).forEach(link -> {
			remotes.put(link.getRel(), link.getUri());
		});
	}
	
	/**
	 * @param name
	 * @return
	 */
	public URI get(final String name) {
		return remotes.get(name);
	}
	
	/**
	 * @return
	 */
	public boolean isEmpty() {
		return remotes.isEmpty();
	}
}
