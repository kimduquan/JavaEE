package epf.gateway;

import java.io.InputStream;
import java.net.URI;
import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;
import org.eclipse.microprofile.health.Readiness;
import org.eclipse.microprofile.jwt.JsonWebToken;
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
    public Response buildRequest(
    		final String service,
    		final JsonWebToken jwt,
    		final HttpHeaders headers, 
            final UriInfo uriInfo,
            final javax.ws.rs.core.Request req,
            final InputStream body) {
    	if(jwt != null && !security.authenticate(jwt, uriInfo)) {
    		throw new NotAuthorizedException(Response.status(Status.UNAUTHORIZED));
    	}
    	final URI serviceUrl = registry.lookup(service).orElseThrow(NotFoundException::new);
    	final Client client = ClientBuilder.newClient();
    	final RequestBuilder builder = new RequestBuilder(client, serviceUrl, req.getMethod(), headers, uriInfo, body, true);
    	final Link self = HATEOAS.selfLink(uriInfo, req, service);
    	Response response = builder.build();
    	response = closeResponse(response);
    	response = buildLinkRequests(client, response, headers, self);
    	response = ResponseUtil.buildResponse(response, uriInfo.getBaseUri());
    	client.close();
    	return response;
    }
    
    /**
     * @param response
     * @return
     */
    private Response closeResponse(final Response response) {
    	response.bufferEntity();
		return response;
    }
    
    /**
     * @param response
     * @param headers
     * @param link
     * @return
     */
    private Response buildLinkRequest(final Client client, final Response response, final HttpHeaders headers, final Link link) {
    	final String service = link.getRel();
		final URI serviceUrl = registry.lookup(service).orElseThrow(NotFoundException::new);
		LOGGER.info(String.format("link=%s", link.toString()));
		final Optional<Duration> wait = HATEOAS.getWait(link);
		wait.ifPresent(duration -> {
			response.bufferEntity();
			ThreadUtil.sleep(duration);
		});
		return HATEOAS.buildLinkRequest(client, serviceUrl, headers, response, link);
    }
    
    /**
     * @param response
     * @param headers
     * @return
     */
    private Response buildLinkRequests(final Client client, final Response response, final HttpHeaders headers, final Link self) {
    	Response linkResponse = response;
    	final List<Link> links = HATEOAS.getRequestLinks(response).collect(Collectors.toList());
    	for(Link link : links) {
    		if(HATEOAS.isRequestLink(link)) {
    			final Link targetLink = HATEOAS.isSelfLink(link) ? self : link;
    			linkResponse = buildLinkRequest(client, linkResponse, headers, targetLink);
    			linkResponse = closeResponse(linkResponse);
    			linkResponse = buildLinkRequests(client, linkResponse, headers, targetLink);
    		}
    	}
    	return linkResponse;
    }
}
