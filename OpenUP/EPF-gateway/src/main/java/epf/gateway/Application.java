package epf.gateway;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URI;
import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.client.Client;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;
import org.eclipse.microprofile.health.Readiness;
import org.eclipse.microprofile.jwt.JsonWebToken;
import epf.client.internal.ClientQueue;
import epf.client.util.RequestBuilder;
import epf.client.util.RequestUtil;
import epf.client.util.ResponseUtil;
import epf.hateoas.utility.HATEOAS;
import epf.util.ThreadUtil;
import epf.util.io.InputStreamUtil;
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
    	final Client client = clients.poll(serviceUrl, null);
    	final RequestBuilder builder = new RequestBuilder(client, serviceUrl, req.getMethod(), headers, uriInfo, body, true);
    	Response response = builder.build();
    	if(isSuccessful(response)) {
        	final Optional<InputStream> entity = HATEOAS.readEntity(response);
        	final Link self = HATEOAS.selfLink(uriInfo, req, service);
        	final boolean isPartial = isPartial(response);
        	final MediaType mediaType = response.getMediaType();
        	response = buildLinkRequests(client, response, entity, mediaType, headers, self, isPartial);
    	}
    	response = ResponseUtil.buildResponse(response, uriInfo.getBaseUri());
    	clients.add(serviceUrl, client);
    	return response;
    }
    
    private boolean isSuccessful(final Response response) {
    	return Response.Status.Family.familyOf(response.getStatus()).compareTo(Response.Status.Family.SUCCESSFUL) == 0;
    }
    
    private boolean isPartial(final Response response) {
    	return Response.Status.PARTIAL_CONTENT.getStatusCode() == response.getStatus();
    }
    
    private Response buildLinkRequest(final Client client, final Response response, final Optional<InputStream> entity, final MediaType mediaType, final HttpHeaders headers, final Link link) {
    	final String service = link.getRel();
		final URI serviceUrl = registry.lookup(service).orElseThrow(NotFoundException::new);
		LOGGER.info(String.format("link=%s", link.toString()));
		final Optional<Duration> wait = HATEOAS.getWait(link);
		wait.ifPresent(duration -> {
			ThreadUtil.sleep(duration);
		});
		return HATEOAS.buildLinkRequest(client, serviceUrl, headers, response, entity, mediaType, link);
    }
    
    private InputStream buildLinkEntity(final MediaType mediaType, final List<Response> linkResponses) {
    	Optional<InputStream> sep = Optional.empty();
		Optional<InputStream> begin = Optional.empty();
		Optional<InputStream> end = Optional.empty();
		InputStream empty = InputStreamUtil.empty();
		if(MediaType.APPLICATION_JSON_TYPE.equals(mediaType)) {
			sep = Optional.of(new ByteArrayInputStream(",".getBytes()));
			begin = Optional.of(new ByteArrayInputStream("[".getBytes()));
			end = Optional.of(new ByteArrayInputStream("]".getBytes()));
			empty = new ByteArrayInputStream("null".getBytes());
		}
		final InputStream nullStream = empty;
		final InputStream streams = linkResponses.stream()
				.map(response -> {
					InputStream stream = nullStream;
					if(response.hasEntity()) {
						final Object entity = response.getEntity();
						if(entity != null) {
							stream = (InputStream) entity;
						}
					}
					return stream;
				})
				.collect(InputStreamUtil.joining(sep, begin, end));
		return streams;
    }
    
    private Response buildLinkResponse(final Response response, final MediaType mediaType, final List<Response> linkResponses) {
		final InputStream entity = buildLinkEntity(mediaType, linkResponses);
    	ResponseBuilder builder = Response.ok(entity).type(mediaType);
    	for(final Entry<String, List<Object>> entry : response.getHeaders().entrySet()) {
			if(entry.getKey().startsWith(RequestUtil.LRA_HTTP_HEADER_PREFIX)) {
				builder = builder.header(entry.getKey(), entry.getValue().get(0));
			}
		}
		return builder.build();
    }
    
    private Response buildLinkRequests(final Client client, final Response response, final Optional<InputStream> entity, final MediaType mediaType, final HttpHeaders headers, final Link self, final boolean isPartial) {
    	Response prevLinkResponse = response;
    	Optional<InputStream> prevLinkEntity = entity;
    	MediaType prevMediaType = mediaType;
    	final List<Response> linkResponses = new CopyOnWriteArrayList<>();
    	final List<Link> links = HATEOAS.getRequestLinks(response).collect(Collectors.toList());
    	for(Link link : links) {
    		if(HATEOAS.isRequestLink(link)) {
    			final Link targetLink = HATEOAS.isSelfLink(link) ? self : link;
    			if(HATEOAS.isSynchronized(link)) {
    				Response linkResponse = buildLinkRequest(client, prevLinkResponse, prevLinkEntity, prevMediaType, headers, targetLink);
        			if(isSuccessful(linkResponse)) {
        				final boolean isPartialLink = isPartial(linkResponse);
        				if(HATEOAS.hasEntity(link)) {
            				prevLinkEntity = HATEOAS.readEntity(linkResponse);
            				prevMediaType = linkResponse.getMediaType();
        				}
        				linkResponse = buildLinkRequests(client, linkResponse, prevLinkEntity, prevMediaType, headers, targetLink, isPartialLink);
        				if(isSuccessful(linkResponse)) {
                			linkResponses.add(linkResponse);
            				prevLinkResponse = linkResponse;
            				prevLinkEntity = HATEOAS.readEntity(linkResponse);
            				prevMediaType = linkResponse.getMediaType();
        				}
        				else {
        					return linkResponse;
        				}
    				}
        			else {
        				return linkResponse;
        			}
    			}
    		}
    	}
    	if(isPartial) {
    		return buildLinkResponse(response, mediaType, linkResponses);
    	}
    	return prevLinkResponse;
    }
}
