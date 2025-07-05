package epf.client.util;

import java.net.URI;
import java.util.function.Function;
import jakarta.ws.rs.client.ClientBuilder;

public interface ClientBuilderUtil {

	jakarta.ws.rs.client.Client newClient(final URI uri, final Function<ClientBuilder, ClientBuilder> buildClient);
	
	void close(final URI uri, final jakarta.ws.rs.client.Client client);
}
