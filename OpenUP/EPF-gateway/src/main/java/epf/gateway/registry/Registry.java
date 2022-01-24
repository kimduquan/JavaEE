package epf.gateway.registry;

import java.io.InputStream;
import java.util.concurrent.CompletionStage;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import epf.gateway.Application;
import epf.naming.Naming;

/**
 * @author PC
 *
 */
@Path(Naming.REGISTRY)
@ApplicationScoped
public class Registry {

	/**
	 * 
	 */
	@Inject
    transient Application request;
    
    /**
     * @param headers
     * @param uriInfo
     * @param req
     * @param body
     * @return
     * @throws Exception 
     */
    @POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public CompletionStage<Response> bind(
    		@Context final SecurityContext context,
    		@Context final HttpHeaders headers, 
            @Context final UriInfo uriInfo,
            @Context final javax.ws.rs.core.Request req,
            final InputStream body
    		) throws Exception {
    	return request.request(context, headers, uriInfo, req, body);
    }
	
	/**
	 * @param headers
	 * @param uriInfo
	 * @param req
	 * @return
	 * @throws Exception 
	 */
	@GET
    public CompletionStage<Response> list(
    		@Context final SecurityContext context,
    		@Context final HttpHeaders headers, 
            @Context final UriInfo uriInfo,
            @Context final javax.ws.rs.core.Request req
    		) throws Exception {
		return request.request(context, headers, uriInfo, req, null);
	}
	
	/**
	 * @param name
	 * @param headers
	 * @param uriInfo
	 * @param req
	 * @return
	 * @throws Exception 
	 */
	@GET
	@Path("{name}")
    public CompletionStage<Response> lookup(
    		@Context 
    		final SecurityContext context,
    		@PathParam("name") 
    		final String name,
    		@Context 
    		final HttpHeaders headers, 
            @Context 
            final UriInfo uriInfo,
            @Context final javax.ws.rs.core.Request req
    		) throws Exception {
		return request.request(context, headers, uriInfo, req, null);
	}
	
	/**
	 * @param name
	 * @param headers
	 * @param uriInfo
	 * @param req
	 * @param body
	 * @return
	 * @throws Exception 
	 */
	@PUT
	@Path("{name}")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public CompletionStage<Response> rebind(
    		@Context 
    		final SecurityContext context,
    		@PathParam("name") 
    		final String name, 
    		@Context 
    		final HttpHeaders headers, 
            @Context 
            final UriInfo uriInfo,
            @Context final javax.ws.rs.core.Request req,
            final InputStream body
    		) throws Exception {
		return request.request(context, headers, uriInfo, req, body);
	}
	
	/**
	 * @param name
	 * @param headers
	 * @param uriInfo
	 * @param req
	 * @return
	 * @throws Exception 
	 */
	@DELETE
	@Path("{name}")
    public CompletionStage<Response> unbind(
    		@Context 
    		final SecurityContext context,
    		@PathParam("name") 
    		final String name,
    		@Context 
    		final HttpHeaders headers, 
            @Context 
            final UriInfo uriInfo,
            @Context final javax.ws.rs.core.Request req
    		) throws Exception {
		return request.request(context, headers, uriInfo, req, null);
	}
}
