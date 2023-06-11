package epf.client.util;

import java.net.URI;
import java.util.function.Function;
import javax.ws.rs.client.ClientBuilder;

/**
 * @author PC
 *
 */
public interface ClientBuilderUtil {

	/**
	 * @param uri
	 * @param buildClient
	 * @return
	 */
	javax.ws.rs.client.Client newClient(final URI uri, final Function<ClientBuilder, ClientBuilder> buildClient);
	
	/**
	 * @param uri
	 * @param client
	 */
	void close(final URI uri, final javax.ws.rs.client.Client client);
}
