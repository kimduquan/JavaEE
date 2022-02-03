package epf.gateway.net;

import java.io.InputStream;
import java.net.URI;
import java.util.concurrent.CompletionStage;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import epf.gateway.Registry;
import epf.gateway.Application;
import epf.naming.Naming;
import epf.util.StringUtil;
import io.smallrye.common.annotation.Blocking;

/**
 *
 * @author FOXCONN
 */
@Blocking
@Path(Naming.NET)
@ApplicationScoped
public class Net {
    
    /**
     * 
     */
    @Inject
    transient Application request;
    
    /**
     * 
     */
    @Inject
    transient Registry registry;
    
    /**
     * @param headers
     * @param uriInfo
     * @param req
     * @param body
     */
    @Path("url")
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
    public CompletionStage<Response> rewriteUrl(
    		@Context final SecurityContext context,
            @Context final HttpHeaders headers, 
            @Context final UriInfo uriInfo,
            @Context final javax.ws.rs.core.Request req,
            final InputStream body
    ) throws Exception {
        return request.request(Naming.NET, context, headers, uriInfo, req, body);
    }
    
    /**
     * @param url
     * @return
     * @throws Exception
     */
    @Path("url")
	@GET
    public CompletionStage<Response> temporaryRedirect(
    		@QueryParam("url")
    		final String url
    ) throws Exception {
    	final int id = StringUtil.fromShortString(url);
    	final URI cacheUrl = registry.lookup(Naming.CACHE).orElseThrow(NotFoundException::new);
    	return ClientBuilder.newClient().target(cacheUrl).path(Naming.NET).path("url").queryParam("id", String.valueOf(id)).request(MediaType.TEXT_PLAIN_TYPE).rx().get(String.class)
    	.thenApply(urlString -> {
    		try {
				return Response.temporaryRedirect(new URI(urlString)).build();
			} 
    		catch (Exception e) {
				return Response.serverError().build();
			}
    	});
    }
}
