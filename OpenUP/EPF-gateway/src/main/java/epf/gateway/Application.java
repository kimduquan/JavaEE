package epf.gateway;

import java.io.InputStream;
import java.net.URI;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.client.Client;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import org.eclipse.microprofile.health.Readiness;
import org.eclipse.microprofile.jwt.JsonWebToken;
import epf.client.util.ClientQueue;
import epf.gateway.internal.RequestBuilder;
import epf.gateway.internal.RequestUtil;

/**
 *
 * @author FOXCONN
 */
@ApplicationScoped
public class Application {
	
	/**
	 * 
	 */
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
    	return builder.build()
    			.thenApply(response -> closeResponse(response, serviceUrl, client))
    			.thenCompose(response -> buildResponse(response, headers));
    }
    
    /**
     * @param response
     * @param serviceUrl
     * @param client
     * @return
     */
    private Response closeResponse(final Response response, final URI serviceUrl, final Client client) {
    	response.bufferEntity();
		clients.add(serviceUrl, client);
		return response;
    }
    
    /**
     * @param response
     * @param headers
     * @return
     */
    private CompletionStage<Response> buildResponse(final Response response, final HttpHeaders headers) {
    	final Optional<Link> firstLink = response.getLinks().stream().findFirst();
    	if(firstLink.isPresent()) {
    		final Link link = firstLink.get();
    		switch(link.getType()) {
    			case "GET":
    			case "POST":
    			case "PUT":
    			case "DELETE":
    			case "HEAD":
    			case "OPTIONS":
    			case "TRACE":
    				final String service = link.getRel();
    				final URI serviceUrl = registry.lookup(service).orElseThrow(NotFoundException::new);
    				final Client client = clients.poll(serviceUrl, null);
    				return RequestUtil.fromResponse(client, serviceUrl, headers, response, link)
    						.whenComplete((res, error) -> closeResponse(response, serviceUrl, client));
    			default:
    				break;
    		}
    	}
    	return CompletableFuture.completedStage(response);
    }
}
