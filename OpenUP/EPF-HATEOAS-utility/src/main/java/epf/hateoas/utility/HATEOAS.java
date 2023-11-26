package epf.hateoas.utility;

import java.io.InputStream;
import java.net.URI;
import java.time.Duration;
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
     * @param link
     * @return
     */
    static Response buildLinkRequest(
    		final Client client,
    		final URI serviceUrl,
    		final HttpHeaders headers,
    		final Response response,
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
				return builder.post(Entity.entity(response.readEntity(InputStream.class), response.getMediaType()));
			case HttpMethod.PUT:
				return builder.put(Entity.entity(response.readEntity(InputStream.class), response.getMediaType()));
			case HttpMethod.DELETE:
				return builder.delete();
			case HttpMethod.HEAD:
				return builder.head();
			case HttpMethod.OPTIONS:
				return builder.options();
			case HttpMethod.PATCH:
				return builder.method(HttpMethod.PATCH, Entity.entity(response.readEntity(InputStream.class), response.getMediaType()));
			default:
				break;
		}
		final Entity<InputStream> entity = response.hasEntity() ? Entity.entity(response.readEntity(InputStream.class), response.getMediaType()) : null;
		return builder.method(link.getType(), entity);
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
    static Optional<Duration> getWait(final Link link) {
    	Objects.requireNonNull(link, "Link");
    	final String wait = link.getParams().get(Naming.Client.Link.WAIT);
    	if(wait != null && !wait.isEmpty()) {
    		return Optional.of(Duration.parse(wait));
    	}
    	return Optional.empty();
    }
    
    /**
     * @param builder
     * @param wait
     * @return
     */
    static Link.Builder setWait(final Link.Builder builder, final Duration wait){
    	Objects.requireNonNull(builder, "Builder");
    	Objects.requireNonNull(wait, "Duration");
    	return builder.param(Naming.Client.Link.WAIT, wait.toString());
    }
}
