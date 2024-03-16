package epf.gateway.security.management;

import java.io.InputStream;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import jakarta.ws.rs.core.UriInfo;
import org.eclipse.microprofile.jwt.JsonWebToken;
import epf.gateway.Application;
import epf.naming.Naming;
import io.smallrye.common.annotation.Blocking;
import io.smallrye.common.annotation.RunOnVirtualThread;

/**
 * 
 */
@Path(Naming.Security.SECURITY_MANAGEMENT)
@ApplicationScoped
public class Management {
    
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
	 * @param context
	 * @param headers
	 * @param uriInfo
	 * @param req
	 * @param body
	 * @return
	 * @throws Exception
	 */
    @PermitAll
	@Path(Naming.Security.CREDENTIAL)
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @RunOnVirtualThread
    @Blocking
	public Response createCredential(
    		@Context final SecurityContext context,
            @Context final HttpHeaders headers, 
            @Context final UriInfo uriInfo,
            @Context final jakarta.ws.rs.core.Request req,
            final InputStream body) throws Exception {
		return request.buildRequest(Naming.Security.SECURITY_MANAGEMENT, null, headers, uriInfo, req, body);
    }
    
    /**
     * @param context
     * @param headers
     * @param uriInfo
     * @param req
     * @param body
     * @return
     * @throws Exception
     */
    @RolesAllowed(Naming.EPF)
    @Path(Naming.Security.CREDENTIAL)
    @PUT
    @RunOnVirtualThread
    @Blocking
    public Response activeCredential(
    		@Context final SecurityContext context,
            @Context final HttpHeaders headers, 
            @Context final UriInfo uriInfo,
            @Context final jakarta.ws.rs.core.Request req,
            final InputStream body) throws Exception {
		return request.buildRequest(Naming.Security.SECURITY_MANAGEMENT, jwt, headers, uriInfo, req, body);
    }
    
    /**
     * @param context
     * @param headers
     * @param uriInfo
     * @param req
     * @param body
     * @return
     * @throws Exception
     */
    @PermitAll
    @Path(Naming.Security.Credential.PASSWORD)
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @RunOnVirtualThread
    @Blocking
    public Response resetPassword(
    		@Context final SecurityContext context,
            @Context final HttpHeaders headers, 
            @Context final UriInfo uriInfo,
            @Context final jakarta.ws.rs.core.Request req,
            final InputStream body) throws Exception {
		return request.buildRequest(Naming.Security.SECURITY_MANAGEMENT, null, headers, uriInfo, req, body);
    }
    
    /**
     * @param context
     * @param headers
     * @param uriInfo
     * @param req
     * @param body
     * @return
     * @throws Exception
     */
    @RolesAllowed(Naming.EPF)
    @Path(Naming.Security.Credential.PASSWORD)
    @PUT
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @RunOnVirtualThread
    @Blocking
    public Response setPassword(
    		@Context final SecurityContext context,
            @Context final HttpHeaders headers, 
            @Context final UriInfo uriInfo,
            @Context final jakarta.ws.rs.core.Request req,
            final InputStream body) throws Exception {
		return request.buildRequest(Naming.Security.SECURITY_MANAGEMENT, jwt, headers, uriInfo, req, body);
    }
}
