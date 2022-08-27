package epf.gateway;

import java.io.InputStream;
import java.net.URI;
import java.util.concurrent.CompletionStage;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.client.Client;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import org.eclipse.microprofile.health.Readiness;
import org.eclipse.microprofile.jwt.JsonWebToken;
import epf.client.util.ClientQueue;
import epf.gateway.internal.RequestBuilder;

/**
 *
 * @author FOXCONN
 */
@ApplicationScoped
public class Application {
	
	@Inject
	transient ClientQueue clients;
    
    /**
     * 
     */
    @Inject @Readiness
    transient Registry registry;
    
    /**
     * @param service
     * @param jwt
     * @param headers
     * @param uriInfo
     * @param req
     * @param body
     * @return
     */
    public CompletionStage<Response> buildRequest(
    		final String service,
    		final JsonWebToken jwt,
    		final HttpHeaders headers, 
            final UriInfo uriInfo,
            final javax.ws.rs.core.Request req,
            final InputStream body) {
    	final URI serviceUrl = registry.lookup(service).orElseThrow(NotFoundException::new);
    	final Client client = clients.poll(serviceUrl, null);
    	final RequestBuilder builder = new RequestBuilder(client, serviceUrl, jwt, req, headers, uriInfo, body);
    	return builder.build().whenComplete((response, error) -> {
    		response.bufferEntity();
    		clients.add(serviceUrl, client);
    	});
    }
}
