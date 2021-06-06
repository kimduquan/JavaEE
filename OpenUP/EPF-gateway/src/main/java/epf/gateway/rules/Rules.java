/**
 * 
 */
package epf.gateway.rules;

import java.io.InputStream;
import java.util.concurrent.CompletionStage;
import javax.enterprise.context.RequestScoped;
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
@RequestScoped
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
            final InputStream body) {
        request.setHeaders(headers);
        request.setUriInfo(uriInfo);
        request.setRequest(req);
        return request.request(body);
    }
	
	/**
	 * @param headers
	 * @param uriInfo
	 * @param req
	 * @param ruleSet
	 * @return
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
            final String ruleSet) {
        request.setHeaders(headers);
        request.setUriInfo(uriInfo);
        request.setRequest(req);
        return request.request(null);
    }
	
	/**
	 * @param headers
	 * @param uriInfo
	 * @param req
	 * @param ruleSet
	 * @return
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
            final String ruleSet) {
        request.setHeaders(headers);
        request.setUriInfo(uriInfo);
        request.setRequest(req);
        return request.request(null);
    }
	
	/**
	 * @param headers
	 * @param uriInfo
	 * @param req
	 * @return
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
    @Asynchronous
	public CompletionStage<Response> getRegistrations(
            @Context final HttpHeaders headers, 
            @Context final UriInfo uriInfo,
            @Context final javax.ws.rs.core.Request req) {
        request.setHeaders(headers);
        request.setUriInfo(uriInfo);
        request.setRequest(req);
        return request.request(null);
    }
}
