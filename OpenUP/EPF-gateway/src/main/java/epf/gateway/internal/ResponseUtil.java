package epf.gateway.internal;

import java.net.URI;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.StatusType;

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
    		final Set<String> methods = response.getAllowedMethods();
    		if(methods != null){
    			builder = builder.allow(methods);
    		}
    		final Map<String, NewCookie> cookies = response.getCookies();
    		if(cookies != null){
    			builder = builder.cookie(cookies.values().toArray(new NewCookie[0]));
    		}
    		final URI location = response.getLocation();
    		if(location != null){
    			builder = builder.contentLocation(location);
    		}
    		if(response.hasEntity()){
            	final Object entity = response.getEntity();
                if(entity != null){
                    builder = builder.entity(entity);
                }
            }
    		final Locale lang = response.getLanguage();
    		if(lang != null){
    			builder = builder.language(lang);
    		}
    		final Date modified = response.getLastModified();
    		if(modified != null){
    			builder = builder.lastModified(modified);
    		}
    		Set<Link> links = response.getLinks();
    		if(links != null){
    			links = links
    					.stream()
    					.map(link -> mapLink(link, baseUri))
    					.collect(Collectors.toSet());
    			builder = builder.links().links(links.toArray(new Link[0]));
    		}
    		if(location != null){
    			builder = builder.location(location);
    		}
    		final StatusType status = response.getStatusInfo();
    		if(status != null){
    			builder = builder.status(status);
    		}
    		final EntityTag tag = response.getEntityTag();
    		if(tag != null){
    			builder = builder.tag(tag);
    		}
    		final MediaType type = response.getMediaType();
    		if(type != null){
    			builder = builder.type(type);
    		}
    		return builder.build();
    	}
    }
}
