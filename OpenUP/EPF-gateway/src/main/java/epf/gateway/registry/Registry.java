package epf.gateway.registry;

import java.io.InputStream;
import java.util.concurrent.CompletionStage;
import javax.enterprise.context.RequestScoped;
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
import javax.ws.rs.core.UriInfo;
import org.eclipse.microprofile.faulttolerance.Asynchronous;
import epf.gateway.Request;

/**
 * @author PC
 *
 */
@Path("registry")
@RequestScoped
public class Registry {

	/**
	 * 
	 */
	@Inject
    private transient Request request;
    
    /**
     * @param headers
     * @param uriInfo
     * @param req
     * @param body
     * @return
     */
    @POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Asynchronous
    public CompletionStage<Response> bind(
    		@Context final HttpHeaders headers, 
            @Context final UriInfo uriInfo,
            @Context final javax.ws.rs.core.Request req,
            final InputStream body
    		) {
    	request.setHeaders(headers);
        request.setUriInfo(uriInfo);
        request.setRequest(req);
        return request.request(body);
    }
	
	/**
	 * @param headers
	 * @param uriInfo
	 * @param req
	 * @return
	 */
	@GET
	@Asynchronous
    public CompletionStage<Response> list(
    		@Context final HttpHeaders headers, 
            @Context final UriInfo uriInfo,
            @Context final javax.ws.rs.core.Request req
    		) {
		request.setHeaders(headers);
        request.setUriInfo(uriInfo);
        request.setRequest(req);
        return request.request(null);
	}
	
	/**
	 * @param name
	 * @param headers
	 * @param uriInfo
	 * @param req
	 * @return
	 */
	@GET
	@Path("{name}")
	@Asynchronous
    public CompletionStage<Response> lookup(
    		@PathParam("name") 
    		final String name,
    		@Context 
    		final HttpHeaders headers, 
            @Context 
            final UriInfo uriInfo,
            @Context final javax.ws.rs.core.Request req
    		) {
		request.setHeaders(headers);
        request.setUriInfo(uriInfo);
        request.setRequest(req);
        return request.request(null);
	}
	
	/**
	 * @param name
	 * @param headers
	 * @param uriInfo
	 * @param req
	 * @param body
	 * @return
	 */
	@PUT
	@Path("{name}")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Asynchronous
    public CompletionStage<Response> rebind(
    		@PathParam("name") 
    		final String name, 
    		@Context 
    		final HttpHeaders headers, 
            @Context 
            final UriInfo uriInfo,
            @Context final javax.ws.rs.core.Request req,
            final InputStream body
    		) {
		request.setHeaders(headers);
        request.setUriInfo(uriInfo);
        request.setRequest(req);
        return request.request(body);
	}
	
	/**
	 * @param name
	 * @param headers
	 * @param uriInfo
	 * @param req
	 * @return
	 */
	@DELETE
	@Path("{name}")
	@Asynchronous
    public CompletionStage<Response> unbind(
    		@PathParam("name") 
    		final String name,
    		@Context 
    		final HttpHeaders headers, 
            @Context 
            final UriInfo uriInfo,
            @Context final javax.ws.rs.core.Request req
    		) {
		request.setHeaders(headers);
        request.setUriInfo(uriInfo);
        request.setRequest(req);
        return request.request(null);
	}
}
