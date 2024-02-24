package epf.hateoas.utility;

import java.io.InputStream;
import java.net.URI;
import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import epf.client.util.LinkComparator;
import epf.client.util.RequestUtil;
import epf.naming.Naming;

/**
 * @author PC
 *
 */
public interface HATEOAS {
    
    /**
     * @param client
     * @param serviceUrl
     * @param headers
     * @param response
     * @param entity
     * @param mediaType
     * @param link
     * @return
     */
    static Response buildLinkRequest(
    		final Client client,
    		final URI serviceUrl,
    		final HttpHeaders headers,
    		final Response response,
    		final Optional<Object> entity,
    		final MediaType mediaType,
    		final Link link
    		) {
    	Objects.requireNonNull(client, "Client");
    	Objects.requireNonNull(serviceUrl, "URI");
    	Objects.requireNonNull(headers, "HttpHeaders");
    	Objects.requireNonNull(response, "Response");
    	Objects.requireNonNull(link, "Link");
    	final URI targetUrl = serviceUrl.resolve(link.getUri());
		final WebTarget target = client.target(targetUrl);
		Invocation.Builder builder = target.request();
		builder = RequestUtil.buildHeaders(builder, headers, targetUrl, true);
		builder = RequestUtil.buildLRAHeaders(builder, response);
		switch(link.getType()) {
			case HttpMethod.GET:
				return builder.get();
			case HttpMethod.POST:
				return builder.post(Entity.entity(entity.orElse(null), mediaType));
			case HttpMethod.PUT:
				return builder.put(Entity.entity(entity.orElse(null), mediaType));
			case HttpMethod.DELETE:
				return builder.delete();
			case HttpMethod.HEAD:
				return builder.head();
			case HttpMethod.OPTIONS:
				return builder.options();
			case HttpMethod.PATCH:
				return builder.method(HttpMethod.PATCH, Entity.entity(entity.orElse(null), mediaType));
			default:
				return builder.method(link.getType(), Entity.entity(entity.orElse(null), mediaType));
		}
    }
    
    /**
     * @param response
     * @return
     */
    static Optional<Object> readEntity(final Response response){
    	Objects.requireNonNull(response, "Response");
    	Optional<Object> entity = Optional.empty();
    	if(response.hasEntity()) {
			entity = Optional.ofNullable(response.readEntity(InputStream.class));
		}
    	return entity;
    }
    
    /**
     * @param response
     * @return
     */
    static Stream<Link> getRequestLinks(final Response response){
    	Objects.requireNonNull(response, "Response");
    	final Comparator<Link> comparator = new LinkComparator();
    	return response.getLinks().stream().filter(link -> link.getType() != null).sorted(comparator);
    }
    
    /**
     * @param uriInfo
     * @param request
     * @param service
     * @return
     */
    static Link selfLink(final UriInfo uriInfo, final Request request, final String service) {
    	Objects.requireNonNull(uriInfo, "UriInfo");
    	Objects.requireNonNull(request, "Request");
    	return Link.fromPath(uriInfo.getPath()).type(request.getMethod()).rel(service).build();
    }
    
    /**
     * @param link
     * @return
     */
    static boolean isSelfLink(final Link link) {
    	Objects.requireNonNull(link, "Link");
    	final String linkRel = link.getRel();
    	if(linkRel != null) {
    		return Naming.Client.Link.SELF.equals(linkRel);
    	}
		return false;
    }
    
    /**
     * @param link
     * @return
     */
    static boolean isSynchronized(final Link link) {
    	Objects.requireNonNull(link, "Link");
    	return "true".equals(link.getParams().getOrDefault("synchronized", "true"));
    }
    
    /**
     * @param link
     * @return
     */
    static boolean hasEntity(final Link link) {
    	Objects.requireNonNull(link, "Link");
    	boolean hasEntity = true;
    	if(link.getType() != null) {
        	switch(link.getType()) {
    			case HttpMethod.GET:
    			case HttpMethod.POST:
    			case HttpMethod.PUT:
    			case HttpMethod.DELETE:
    			case HttpMethod.OPTIONS:
    			case HttpMethod.PATCH:
    				break;
    			case HttpMethod.HEAD:
    				hasEntity = false;
    			default:
    				break;
        	}
    	}
    	return hasEntity;
    }
    
    /**
     * @param link
     * @return
     */
    static boolean isRequestLink(final Link link) {
    	Objects.requireNonNull(link, "Link");
    	if(link.getType() != null) {
        	switch(link.getType()) {
    			case HttpMethod.GET:
    			case HttpMethod.POST:
    			case HttpMethod.PUT:
    			case HttpMethod.DELETE:
    			case HttpMethod.HEAD:
    			case HttpMethod.OPTIONS:
    			case HttpMethod.PATCH:
    				return true;
    			default:
    				break;
        	}
    	}
    	return false;
    }
    
    /**
     * @param link
     * @return
     */
    static Optional<String> synchronized_(final Link link) {
    	return Optional.ofNullable(link.getParams().get("synchronized"));
    }
    
    /**
     * @param link
     * @return
     */
    static Optional<String> continue_(final Link link) {
    	return Optional.ofNullable(link.getParams().get("continue"));
    }
}
