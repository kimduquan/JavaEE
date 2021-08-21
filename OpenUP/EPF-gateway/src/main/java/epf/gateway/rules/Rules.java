/**
 * 
 */
package epf.gateway.rules;

import java.io.InputStream;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
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
@Path("rules")
@ApplicationScoped
public class Rules {
	
    /**
     * 
     */
    @Inject
    private transient Request request;

	/**
	 * @param headers
	 * @param uriInfo
	 * @param req
	 * @param ruleSet
	 * @param body
	 * @return
	 * @throws Exception 
	 */
	@POST
	@Path("{ruleSet}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
    @Asynchronous
	public CompletionStage<Response> executeRules(
            @Context final HttpHeaders headers, 
            @Context final UriInfo uriInfo,
            @Context final javax.ws.rs.core.Request req,
            @PathParam("ruleSet")
            final String ruleSet,
            final InputStream body) throws Exception {
        return CompletableFuture.completedFuture(request.request(headers, uriInfo, req, body));
    }
	
	/**
	 * @param headers
	 * @param uriInfo
	 * @param req
	 * @param ruleSet
	 * @return
	 * @throws Exception 
	 */
	@PUT
	@Path("{ruleSet}")
	@Produces(MediaType.APPLICATION_JSON)
    @Asynchronous
	public CompletionStage<Response> executeRules(
            @Context final HttpHeaders headers, 
            @Context final UriInfo uriInfo,
            @Context final javax.ws.rs.core.Request req,
            @PathParam("ruleSet")
            final String ruleSet) throws Exception {
        return CompletableFuture.completedFuture(request.request(headers, uriInfo, req, null));
    }
	
	/**
	 * @param headers
	 * @param uriInfo
	 * @param req
	 * @param ruleSet
	 * @return
	 * @throws Exception 
	 */
	@PATCH
	@Path("{ruleSet}")
	@Consumes(MediaType.APPLICATION_JSON)
    @Asynchronous
	public CompletionStage<Response> addObject(
            @Context final HttpHeaders headers, 
            @Context final UriInfo uriInfo,
            @Context final javax.ws.rs.core.Request req,
            @PathParam("ruleSet")
            final String ruleSet) throws Exception {
        return CompletableFuture.completedFuture(request.request(headers, uriInfo, req, null));
    }
	
	/**
	 * @param headers
	 * @param uriInfo
	 * @param req
	 * @return
	 * @throws Exception 
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
    @Asynchronous
	public CompletionStage<Response> getRegistrations(
            @Context final HttpHeaders headers, 
            @Context final UriInfo uriInfo,
            @Context final javax.ws.rs.core.Request req) throws Exception {
        return CompletableFuture.completedFuture(request.request(headers, uriInfo, req, null));
    }
}
