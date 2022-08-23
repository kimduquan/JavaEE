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
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import org.eclipse.microprofile.jwt.JsonWebToken;
import epf.gateway.Application;
import epf.gateway.internal.ResponseUtil;
import epf.naming.Naming;

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
    public CompletionStage<Response> registerRuleExecutionSet(
    		@Context final SecurityContext context,
            @Context final HttpHeaders headers, 
            @Context final UriInfo uriInfo,
            @Context final javax.ws.rs.core.Request req,
            @PathParam("ruleSet")
            final String ruleSet,
            final InputStream body) throws Exception {
		final Client client = ClientBuilder.newClient();
    	return ResponseUtil.buildResponse(client, request.buildRequest(client, Naming.RULES, jwt, headers, uriInfo, req, body), uriInfo.getBaseUri());
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
		final Client client = ClientBuilder.newClient();
    	return ResponseUtil.buildResponse(client, request.buildRequest(client, Naming.RULES, jwt, headers, uriInfo, req, null), uriInfo.getBaseUri());
    }
}
