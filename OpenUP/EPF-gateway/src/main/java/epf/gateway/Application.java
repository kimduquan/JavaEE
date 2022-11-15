package epf.gateway;

import java.io.InputStream;
import java.net.URI;
import java.time.Duration;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.client.Client;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import org.eclipse.microprofile.health.Readiness;
import org.eclipse.microprofile.jwt.JsonWebToken;
import epf.client.util.ClientQueue;
import epf.gateway.internal.LinkComparator;
import epf.gateway.internal.RequestBuilder;
import epf.gateway.internal.RequestUtil;
import epf.gateway.internal.ResponseUtil;
import epf.naming.Naming;
import epf.util.logging.LogManager;

/**
 *
 * @author FOXCONN
 */
@ApplicationScoped
public class Application {
	
	/**
	 * 
	 */
	private transient static final Logger LOGGER = LogManager.getLogger(Application.class.getName());
	
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
    			.thenCompose(response -> buildLinkRequests(response, headers))
    			.thenApply(response -> ResponseUtil.buildResponse(response, uriInfo.getBaseUri()));
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
     * @param link
     * @return
     */
    private CompletionStage<Response> buildLinkRequest(final Response response, final HttpHeaders headers, final Link link) {
    	final String service = link.getRel();
		final URI serviceUrl = registry.lookup(service).orElseThrow(NotFoundException::new);
		final Client client = clients.poll(serviceUrl, null);
		LOGGER.info(String.format("link=%s", link.toString()));
		final String wait = link.getParams().get(Naming.Client.Internal.LINK_PARAM_WAIT);
		if(wait != null) {
			response.bufferEntity();
			try {
				Thread.sleep(Duration.parse(wait).toMillis());
			}
			catch(Exception ex) {
				
			}
		}
		return RequestUtil.buildLinkRequest(client, serviceUrl, headers, response, link)
				.whenComplete((res, error) -> closeResponse(response, serviceUrl, client));
    }
    
    /**
     * @param response
     * @param headers
     * @return
     */
    private CompletionStage<Response> buildLinkRequests(final Response response, final HttpHeaders headers) {
    	CompletionStage<Response> linkResponse = CompletableFuture.completedStage(response);
    	final Comparator<Link> comparator = new LinkComparator();
    	final List<Link> links = response.getLinks().stream().filter(link -> link.getType() != null).sorted(comparator).collect(Collectors.toList());
    	for(Link link : links) {
    		switch(link.getType()) {
				case HttpMethod.GET:
				case HttpMethod.POST:
				case HttpMethod.PUT:
				case HttpMethod.DELETE:
				case HttpMethod.HEAD:
				case HttpMethod.OPTIONS:
				case HttpMethod.PATCH:
					linkResponse = linkResponse.thenCompose(r -> buildLinkRequest(r, headers, link)).thenCompose(r -> buildLinkRequests(r, headers));
					break;
				default:
					break;
			}
    	}
    	return linkResponse;
    }
}
