package epf.gateway.net;

import java.io.InputStream;
import java.net.URI;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
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
            @Context final javax.ws.rs.core.Request req,
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
