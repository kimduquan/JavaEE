package epf.gateway.rules.admin;

import java.io.InputStream;
import java.util.concurrent.CompletionStage;
import javax.annotation.security.RolesAllowed;
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
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import epf.gateway.Application;
import epf.naming.Naming;
import io.smallrye.common.annotation.Blocking;

/**
 * @author PC
 *
 */
@Blocking
@Path(Naming.Rules.RULES_ADMIN)
@ApplicationScoped
@RolesAllowed(Naming.Security.DEFAULT_ROLE)
public class Admin {
	
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
    @Path("{ruleSet}")
    @PUT
    @Consumes(MediaType.APPLICATION_OCTET_STREAM)
    public CompletionStage<Response> registerRuleExecutionSet(
    		@Context final SecurityContext context,
            @Context final HttpHeaders headers, 
            @Context final UriInfo uriInfo,
            @Context final javax.ws.rs.core.Request req,
            @PathParam("ruleSet")
            final String ruleSet,
            final InputStream body) throws Exception {
        return request.request(Naming.RULES, context, headers, uriInfo, req, body);
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
    public CompletionStage<Response> deregisterRuleExecutionSet(
    		@Context final SecurityContext context,
            @Context final HttpHeaders headers, 
            @Context final UriInfo uriInfo,
            @Context final javax.ws.rs.core.Request req,
            @PathParam("ruleSet")
            final String ruleSet) throws Exception {
        return request.request(Naming.RULES, context, headers, uriInfo, req, null);
    }
}
