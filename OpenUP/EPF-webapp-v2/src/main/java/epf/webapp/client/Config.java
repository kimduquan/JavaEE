package epf.webapp.client;

import java.util.Map;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import epf.webapp.internal.AuthFilter;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.QueryParam;

@RegisterRestClient
@RegisterProvider(AuthFilter.class)
public interface Config {

	@GET
	Map<String, String> getProperties(@QueryParam("name") final String name) throws Exception;
}
