package epf.gateway.net;

import java.io.InputStream;
import java.net.URI;
import java.util.Map;
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
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import epf.gateway.Registry;
import epf.gateway.Application;
import epf.naming.Naming;
import epf.util.StringUtil;

/**
 *
 * @author FOXCONN
 */
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
    	final long id = StringUtil.fromShortString(url);
    	final URI queryUrl = registry.lookup(Naming.QUERY).orElseThrow(NotFoundException::new);
    	return ClientBuilder.newClient().target(queryUrl).path("entity").path("EPF_Net").path("URL").path(String.valueOf(id)).request(MediaType.APPLICATION_JSON).rx()
    	.get()
    	.thenApply(response -> {
    		if(response.getStatus() == 200) {
    			final Map<String, Object> map = response.readEntity(new GenericType<Map<String, Object>>(){});
    			final String string = String.valueOf(map.get("string"));
    			try {
					return Response.temporaryRedirect(new URI(string)).build();
				} 
    			catch (Exception e) {
					return response;
				}
    		}
    		return response;
    	});
    }
}
