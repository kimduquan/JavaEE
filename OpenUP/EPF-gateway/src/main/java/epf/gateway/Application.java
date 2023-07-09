package epf.gateway;

import java.io.InputStream;
import java.net.URI;
import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.client.Client;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import org.eclipse.microprofile.health.Readiness;
import org.eclipse.microprofile.jwt.JsonWebToken;
import epf.client.internal.ClientQueue;
import epf.client.util.RequestBuilder;
import epf.client.util.ResponseUtil;
import epf.hateoas.utility.HATEOAS;
import epf.util.ThreadUtil;
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
    @Inject 
    @Readiness
    transient Registry registry;
    
    /**
     * 
     */
    @Inject
    @Readiness
    transient Security security;
    
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
    	if(jwt != null && !security.authenticate(jwt)) {
    		throw new ForbiddenException();
    	}
    	final URI serviceUrl = registry.lookup(service).orElseThrow(NotFoundException::new);
    	final Client client = clients.poll(serviceUrl, b -> b);
    	final RequestBuilder builder = new RequestBuilder(client, serviceUrl, req.getMethod(), headers, uriInfo, body, true);
    	final Link self = HATEOAS.selfLink(uriInfo, req, service);
    	return builder.build()
    			.thenApply(response -> closeResponse(response, serviceUrl, client))
    			.thenCompose(response -> buildLinkRequests(response, headers, self))
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
		final Optional<Duration> wait = HATEOAS.getWait(link);
		wait.ifPresent(duration -> {
			response.bufferEntity();
			ThreadUtil.sleep(duration);
		});
		return HATEOAS.buildLinkRequest(client, serviceUrl, headers, response, link)
				.whenComplete((res, error) -> closeResponse(response, serviceUrl, client));
    }
    
    /**
     * @param response
     * @param headers
     * @return
     */
    private CompletionStage<Response> buildLinkRequests(final Response response, final HttpHeaders headers, final Link self) {
    	CompletionStage<Response> linkResponse = CompletableFuture.completedFuture(response);
    	final List<Link> links = HATEOAS.getRequestLinks(response).collect(Collectors.toList());
    	for(Link link : links) {
    		if(HATEOAS.isRequestLink(link)) {
    			final Link targetLink = HATEOAS.isSelfLink(link) ? self : link;
				linkResponse = linkResponse.thenCompose(r -> buildLinkRequest(r, headers, targetLink)).thenCompose(r -> buildLinkRequests(r, headers, targetLink));
    		}
    	}
    	return linkResponse;
    }
}
