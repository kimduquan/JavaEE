package epf.gateway.rules;

import java.io.InputStream;
import javax.annotation.security.RolesAllowed;
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
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import org.eclipse.microprofile.jwt.JsonWebToken;
import epf.gateway.Application;
import epf.naming.Naming;
import io.smallrye.common.annotation.Blocking;
import io.smallrye.common.annotation.RunOnVirtualThread;

/**
 * @author PC
 *
 */
@Path(Naming.RULES)
@ApplicationScoped
@RolesAllowed(Naming.Security.DEFAULT_ROLE)
public class Rules {
	
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
	 * @param ruleSet
	 * @param body
	 * @return
	 * @throws Exception 
	 */
	@POST
	@Path("{ruleSet}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
    @RunOnVirtualThread
    @Blocking
	public Response executeRules(
    		@Context final SecurityContext context,
            @Context final HttpHeaders headers, 
            @Context final UriInfo uriInfo,
            @Context final javax.ws.rs.core.Request req,
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
	@PUT
	@Path("{ruleSet}")
	@Produces(MediaType.APPLICATION_JSON)
    @RunOnVirtualThread
    @Blocking
	public Response executeRules(
    		@Context final SecurityContext context,
            @Context final HttpHeaders headers, 
            @Context final UriInfo uriInfo,
            @Context final javax.ws.rs.core.Request req,
            @PathParam("ruleSet")
            final String ruleSet) throws Exception {
		return request.buildRequest(Naming.RULES, jwt, headers, uriInfo, req, null);
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
    @RunOnVirtualThread
    @Blocking
	public Response addObject(
    		@Context final SecurityContext context,
            @Context final HttpHeaders headers, 
            @Context final UriInfo uriInfo,
            @Context final javax.ws.rs.core.Request req,
            @PathParam("ruleSet")
            final String ruleSet) throws Exception {
		return request.buildRequest(Naming.RULES, jwt, headers, uriInfo, req, null);
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
    @RunOnVirtualThread
    @Blocking
	public Response getRegistrations(
    		@Context final SecurityContext context,
            @Context final HttpHeaders headers, 
            @Context final UriInfo uriInfo,
            @Context final javax.ws.rs.core.Request req) throws Exception {
		return request.buildRequest(Naming.RULES, jwt, headers, uriInfo, req, null);
    }
}
