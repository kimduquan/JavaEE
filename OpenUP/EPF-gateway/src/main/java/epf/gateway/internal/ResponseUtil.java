package epf.gateway.internal;

import java.io.InputStream;
import java.net.URI;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.Response.ResponseBuilder;

/**
 * @author PC
 *
 */
public interface ResponseUtil {
	
	/**
	 * 
	 */
	int MIN_PATHS_COUNT = 1;
    
    /**
     * @param link
     * @param baseUri
     * @return
     */
    static URI mapUri(final Link link, final URI baseUri) {
    	final URI linkUri = link.getUri();
    	final String[] linkPaths = linkUri.getPath().split("/");
    	String path = "";
    	if(linkPaths.length > MIN_PATHS_COUNT) {
    		path = String.join("/", Arrays.asList(linkPaths).subList(2, linkPaths.length));
    	}
    	return UriBuilder.fromUri(baseUri).path(path).build();
    }
    
    /**
     * @param link
     * @param baseUri
     * @return
     */
    static Link mapLink(final Link link, final URI baseUri) {
    	final URI uri = mapUri(link, baseUri);
    	return Link.fromLink(link).uri(uri).build();
    }
    
    /**
     * @param response
     * @param baseUri
     * @return
     */
    static Response buildResponse(final Response response, final URI baseUri){
    	ResponseBuilder builder = Response.fromResponse(response);
		final InputStream input = response.readEntity(InputStream.class);
		builder = builder.entity(input);
		Set<Link> links = response.getLinks();
		if(links != null){
			links = links
					.stream()
					.filter(link -> link.getType() == null)
					.map(link -> mapLink(link, baseUri))
					.collect(Collectors.toSet());
			builder = builder.links().links(links.toArray(new Link[0]));
		}
		final Response newResponse = builder.build();
		return newResponse;
    }
}
