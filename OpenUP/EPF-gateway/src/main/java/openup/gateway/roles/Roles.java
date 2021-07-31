/**
 * 
 */
package openup.gateway.roles;

import java.util.concurrent.CompletionStage;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
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
@Path("openup/roles")
@RequestScoped
public class Roles {
	
	/**
     * 
     */
    @Inject
    private transient Request request;

    /**
     * @param headers
     * @param uriInfo
     * @param req
     * @return
     */
    @GET
	@Produces(MediaType.APPLICATION_JSON)
    @Asynchronous
	public CompletionStage<Response> getRoles(
            @Context final HttpHeaders headers, 
            @Context final UriInfo uriInfo,
            @Context final javax.ws.rs.core.Request req) {
        request.setHeaders(headers);
        request.setUriInfo(uriInfo);
        request.setRequest(req);
        return request.request(null);
    }
    
    /**
     * @param headers
     * @param uriInfo
     * @param req
     * @param roleSet
     * @param role
     */
    @GET
	@Path("{roleSet}/{role}")
	@Produces(MediaType.APPLICATION_JSON)
    @Asynchronous
	public CompletionStage<Response> getRoles(
            @Context final HttpHeaders headers, 
            @Context final UriInfo uriInfo,
            @Context final javax.ws.rs.core.Request req,
            @PathParam("roleSet") final String roleSet, 
            @PathParam("role") final String role) {
        request.setHeaders(headers);
        request.setUriInfo(uriInfo);
        request.setRequest(req);
        return request.request(null);
    }
}
