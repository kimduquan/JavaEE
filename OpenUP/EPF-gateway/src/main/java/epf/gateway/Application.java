package epf.gateway;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.time.Duration;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.HttpMethod;
import jakarta.ws.rs.NotAuthorizedException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Link;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.ResponseBuilder;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.core.UriInfo;
import org.eclipse.microprofile.health.Readiness;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.rest.client.RestClientBuilder;
import epf.concurrent.client.ext.Concurrent;
import epf.concurrent.client.ext.Synchronized;
import epf.gateway.util.HATEOAS;
import epf.gateway.util.RequestBuilder;
import epf.gateway.util.RequestUtil;
import epf.gateway.util.ResponseUtil;
import epf.gateway.util.StreamClient;
import epf.gateway.util.Streaming;
import epf.naming.Naming;
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
    @Readiness
    transient Registry registry;
    
    /**
     * 
     */
    @Inject
    @Readiness
    transient Security security;
    
    /**
     * 
     */
    @Inject
    transient Concurrent concurrent;
    
    /**
     * 
     */
    @Inject
    transient Stream stream;
    
    /**
     * 
     */
    @PostConstruct
    protected void postConstruct() {
    	final Optional<URI> uri = registry.lookup(Naming.CONCURRENT);
    	try {
    		LOGGER.info("connect to server:" + uri.get());
			concurrent.connectToServer(uri.get());
		} 
    	catch (Exception e) {
			LOGGER.log(Level.SEVERE, "postConstruct", e);
		}
    }
    
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
            final jakarta.ws.rs.core.Request req,
            final InputStream body) throws Exception {
    	if(jwt != null && !security.authenticate(jwt, uriInfo)) {
    		throw new NotAuthorizedException(Response.status(Status.UNAUTHORIZED));
    	}
    	final URI serviceUrl = registry.lookup(service).orElseThrow(NotFoundException::new);
    	final Client client = ClientBuilder.newClient();
    	final RequestBuilder builder = new RequestBuilder(client, serviceUrl, req.getMethod(), headers, uriInfo, body, true);
    	Response response = builder.build();
    	final Optional<Object> entity = HATEOAS.readEntity(response);
    	if(isSuccessful(response)) {
        	final Link self = HATEOAS.selfLink(uriInfo, req, service);
        	final boolean isPartial = isPartial(response);
        	final MediaType mediaType = response.getMediaType();
        	response = buildLinkRequests(client, response, entity, mediaType, headers, self, isPartial);
    	}
    	response = ResponseUtil.buildResponse(response, entity, uriInfo.getBaseUri());
    	client.close();
    	return response;
    }
    
    private boolean isSuccessful(final Response response) {
    	return Response.Status.Family.familyOf(response.getStatus()).compareTo(Response.Status.Family.SUCCESSFUL) == 0;
    }
    
    private boolean isPartial(final Response response) {
    	return Response.Status.PARTIAL_CONTENT.getStatusCode() == response.getStatus();
    }
    
    private URI lookup(final Link link) {
    	final String service = link.getRel();
		final URI serviceUrl = registry.lookup(service).orElseThrow(NotFoundException::new);
		LOGGER.info(String.format("link=%s", link.toString()));
		return serviceUrl;
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
    	ResponseBuilder builder = Response.status(response.getStatus()).entity(entity).type(mediaType);
    	for(final Entry<String, List<Object>> entry : response.getHeaders().entrySet()) {
			if(entry.getKey().startsWith(RequestUtil.LRA_HTTP_HEADER_PREFIX)) {
				builder = builder.header(entry.getKey(), entry.getValue().get(0));
			}
		}
		return builder.build();
    }
    
    private void synchronized_(final Link targetLink) throws Exception {
    	final Optional<String> synchronized_ = HATEOAS.synchronized_(targetLink);
		if(synchronized_.isPresent()) {
			final Synchronized _synchronized = concurrent.synchronized_(synchronized_.get());
			final Optional<String> time = HATEOAS.time(targetLink);
			if(time.isPresent()) {
				_synchronized.synchronized_(Duration.parse(time.get()));
			}
			else {
				_synchronized.synchronized_();
			}
		}
		final Optional<String> continue_ = HATEOAS.continue_(targetLink);
		if(continue_.isPresent()) {
			concurrent.synchronized_(synchronized_.get()).return_();
		}
    }
    
    private CompletionStage<InputStream> buildStreamRequest(
    		final URI serviceUrl,
    		final Optional<Object> entity,
    		final Link link
    		) throws Exception {
    	final InputStream input = entity.isPresent() ? (InputStream)entity.get() : null;
    	try(BufferedReader reader = new BufferedReader(new InputStreamReader(input))) {
    		final String string = reader.lines().collect(Collectors.joining());
    		LOGGER.info("string:" + string);
    	}
    	final StreamClient stream = RestClientBuilder.newBuilder().baseUri(serviceUrl.resolve(link.getUri())).build(StreamClient.class);
    	switch(link.getType()) {
			case HttpMethod.GET:
				return stream.get();
			case HttpMethod.POST:
				return stream.post(input);
			case HttpMethod.PUT:
				return stream.put(input);
			case HttpMethod.DELETE:
				return stream.delete();
			case HttpMethod.HEAD:
				return stream.head();
			case HttpMethod.OPTIONS:
				return stream.options();
			case HttpMethod.PATCH:
				return stream.patch(input);
			default:
				return null;
		}
    }
    
    private Response buildLinkRequests(final Client client, final Response response, final Optional<Object> entity, final MediaType mediaType, final HttpHeaders headers, final Link self, final boolean isPartial) throws Exception {
    	Response prevLinkResponse = response;
    	Optional<Object> prevLinkEntity = entity;
    	MediaType prevMediaType = mediaType;
    	Iterator<Object> partialEntityIterator = Arrays.asList().iterator();
    	if(isPartial) {
    		final List<Object> partialEntities = response.readEntity(new GenericType<List<Object>>() {});
    		partialEntityIterator = partialEntities.iterator();
    	}
    	final List<Response> linkResponses = new CopyOnWriteArrayList<>();
    	final List<Link> links = HATEOAS.getRequestLinks(response).collect(Collectors.toList());
    	for(Link link : links) {
    		if(HATEOAS.isRequestLink(link)) {
    			final Link targetLink = HATEOAS.isSelfLink(link) ? self : link;
    			if(partialEntityIterator.hasNext()) {
    				prevLinkEntity = Optional.ofNullable(partialEntityIterator.next());
    				prevMediaType = mediaType;
    			}
    			if(HATEOAS.isSynchronized(link)) {
    				synchronized_(targetLink);
    			}
    			final URI serviceUrl = lookup(targetLink);
    			final Optional<String> volatile_ = HATEOAS.volatile_(targetLink);
    			if(volatile_.isPresent()) {
    				//final CompletionStage<InputStream> linkStream = HATEOAS.buildStreamRequest(client, serviceUrl, headers, prevLinkResponse, prevLinkEntity, prevMediaType, targetLink);
    				final CompletionStage<InputStream> linkStream = buildStreamRequest(serviceUrl, prevLinkEntity, targetLink);
    				final Streaming streaming = stream.stream(volatile_.get(), linkStream);
    				if(streaming.getThrowable() != null) {
    					throw (Exception)streaming.getThrowable();
    				}
    				Response linkResponse = Response.ok().build();
        			linkResponses.add(linkResponse);
    				prevLinkResponse = linkResponse;
    				prevLinkEntity = HATEOAS.readEntity(linkResponse);
    				prevMediaType = linkResponse.getMediaType();
    			}
    			else {
        			Response linkResponse = HATEOAS.buildLinkRequest(client, serviceUrl, headers, prevLinkResponse, prevLinkEntity, prevMediaType, targetLink);
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
    		else if(HATEOAS.isStreamLink(link)) {
    			final Link targetLink = HATEOAS.isSelfLink(link) ? self : link;
    			final URI serviceUrl = lookup(targetLink);
    			final Streaming stream = Streaming.connectToServer(serviceUrl.resolve(targetLink.getUri()));
    			if(prevLinkEntity.isPresent()) {
    				final Optional<String> volatile_ = HATEOAS.volatile_(targetLink);
    				if(volatile_.isPresent()) {
        				stream.send(volatile_.get(), (InputStream)prevLinkEntity.get());
    				}
    				else {
        				stream.send((InputStream)prevLinkEntity.get());
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
