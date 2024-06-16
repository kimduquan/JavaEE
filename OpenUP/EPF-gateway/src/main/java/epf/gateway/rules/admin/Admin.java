package epf.gateway.rules.admin;

import java.io.InputStream;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import jakarta.ws.rs.core.UriInfo;
import org.eclipse.microprofile.jwt.JsonWebToken;
import epf.gateway.Application;
import epf.naming.Naming;
import io.smallrye.common.annotation.RunOnVirtualThread;

/**
 * @author PC
 *
 */
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
     * 
     */
    @Inject
    transient JsonWebToken jwt;
    
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
    @RunOnVirtualThread
    public Response registerRuleExecutionSet(
    		@Context final SecurityContext context,
            @Context final HttpHeaders headers, 
            @Context final UriInfo uriInfo,
            @Context final jakarta.ws.rs.core.Request req,
            @PathParam("ruleSet")
            final String ruleSet,
            final InputStream body) throws Exception {
		return request.buildRequest(Naming.RULES, jwt, headers, uriInfo, req, body);
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
    @RunOnVirtualThread
    public Response deregisterRuleExecutionSet(
    		@Context final SecurityContext context,
            @Context final HttpHeaders headers, 
            @Context final UriInfo uriInfo,
            @Context final jakarta.ws.rs.core.Request req,
            @PathParam("ruleSet")
            final String ruleSet) throws Exception {
		return request.buildRequest(Naming.RULES, jwt, headers, uriInfo, req, null);
    }
}
