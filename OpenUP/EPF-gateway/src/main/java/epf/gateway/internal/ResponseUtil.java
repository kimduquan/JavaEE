package epf.gateway.internal;

import java.net.URI;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import javax.ws.rs.client.Client;
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
     * @param builder
     * @param res
     * @param uriInfo
     * @return
     */
    static Response buildResponse(final Response res, final URI baseUri){
    	try(Response response = res){
    		response.bufferEntity();
    		ResponseBuilder builder = Response.fromResponse(response);
    		Set<Link> links = response.getLinks();
    		if(links != null){
    			links = links
    					.stream()
    					.map(link -> mapLink(link, baseUri))
    					.collect(Collectors.toSet());
    			builder = builder.links().links(links.toArray(new Link[0]));
    		}
    		return builder.build();
    	}
    }
    
    /**
     * @param client
     * @param response
     * @param error
     * @return
     */
    static void closeResponse(final Client client, final Response response, final Throwable error) {
		client.close();
    	if(error != null) {
    		response.close();
		}
    }
}
