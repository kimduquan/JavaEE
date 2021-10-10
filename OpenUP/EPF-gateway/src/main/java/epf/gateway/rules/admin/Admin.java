/**
 * 
 */
package epf.gateway.rules.admin;

import java.io.InputStream;
import java.util.concurrent.CompletionStage;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
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
@Path("rules/admin")
@ApplicationScoped
public class Admin {
	
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
     * @throws Exception 
     */
    @Path("{ruleSet}")
    @PUT
    @Consumes(MediaType.APPLICATION_OCTET_STREAM)
    @Asynchronous
    public CompletionStage<Response> registerRuleExecutionSet(
            @Context final HttpHeaders headers, 
            @Context final UriInfo uriInfo,
            @Context final javax.ws.rs.core.Request req,
            @PathParam("ruleSet")
            final String ruleSet,
            final InputStream body) throws Exception {
        return request.request(headers, uriInfo, req, body);
    }
    
    /**
     * @param headers
     * @param uriInfo
     * @param req
     * @param ruleSet
     * @return
     * @throws Exception 
     */
    @Path("{ruleSet}")
    @DELETE
    @Asynchronous
    public CompletionStage<Response> deregisterRuleExecutionSet(
            @Context final HttpHeaders headers, 
            @Context final UriInfo uriInfo,
            @Context final javax.ws.rs.core.Request req,
            @PathParam("ruleSet")
            final String ruleSet) throws Exception {
        return request.request(headers, uriInfo, req, null);
    }
}
