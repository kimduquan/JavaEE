package epf.gateway.internal;

import java.io.InputStream;
import java.net.URI;
import java.util.Set;
import java.util.stream.Collectors;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

/**
 * @author PC
 *
 */
public interface ResponseUtil {
    
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
					.collect(Collectors.toSet());
			builder = builder.links().links(links.toArray(new Link[0]));
		}
		final Response newResponse = builder.build();
		return newResponse;
    }
}
