package epf.gateway.net;

import java.io.InputStream;
import java.net.URI;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import jakarta.ws.rs.core.UriInfo;
import org.eclipse.microprofile.health.Readiness;
import epf.gateway.Registry;
import epf.gateway.Application;
import epf.naming.Naming;
import epf.util.StringUtil;
import epf.util.logging.LogManager;
import io.smallrye.common.annotation.Blocking;
import io.smallrye.common.annotation.RunOnVirtualThread;

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
	private transient static final Logger LOGGER = LogManager.getLogger(Net.class.getName());
    
    /**
     * 
     */
    @Inject
    transient Application request;
    
    /**
     * 
     */
    @Inject @Readiness
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
    @RunOnVirtualThread
    @Blocking
    public Response rewriteUrl(
    		@Context final SecurityContext context,
            @Context final HttpHeaders headers, 
            @Context final UriInfo uriInfo,
            @Context final jakarta.ws.rs.core.Request req,
            final InputStream body
    ) throws Exception {
    	return request.buildRequest(Naming.NET, null, headers, uriInfo, req, body);
    }
    
    /**
     * @param url
     * @return
     * @throws Exception
     */
    @Path("url")
	@GET
    @RunOnVirtualThread
    @Blocking
    public Response temporaryRedirect(
    		@QueryParam("url")
    		final String url
    ) throws Exception {
    	final long id = StringUtil.fromShortString(url);
    	final URI queryUrl = registry.lookup(Naming.QUERY).orElseThrow(NotFoundException::new);
    	final Client client = ClientBuilder.newClient();
    	try(Response response = client.target(queryUrl)
    			.path("entity")
    			.path("EPF_Net")
    			.path("URL")
    			.path(String.valueOf(id))
    			.request(MediaType.APPLICATION_JSON)
    			.get()){
    		try {
    			if(response.getStatus() == 200) {
					final Map<String, Object> map = response.readEntity(new GenericType<Map<String, Object>>(){});
					final String string = String.valueOf(map.get("string"));
					return Response.temporaryRedirect(new URI(string)).build();
		    	}
			} 
			catch (Exception e) {
				LOGGER.log(Level.SEVERE, e.getMessage(), e);
			}
	    	return response;
    	}
    	finally {
    		client.close();
    	}
    }
}
