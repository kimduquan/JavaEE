package epf.gateway.security.management;

import java.io.InputStream;
import java.util.concurrent.CompletionStage;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
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
	public CompletionStage<Response> createCredential(
    		@Context final SecurityContext context,
            @Context final HttpHeaders headers, 
            @Context final UriInfo uriInfo,
            @Context final javax.ws.rs.core.Request req,
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
    public CompletionStage<Response> activeCredential(
    		@Context final SecurityContext context,
            @Context final HttpHeaders headers, 
            @Context final UriInfo uriInfo,
            @Context final javax.ws.rs.core.Request req,
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
    public CompletionStage<Response> resetPassword(
    		@Context final SecurityContext context,
            @Context final HttpHeaders headers, 
            @Context final UriInfo uriInfo,
            @Context final javax.ws.rs.core.Request req,
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
    public CompletionStage<Response> setPassword(
    		@Context final SecurityContext context,
            @Context final HttpHeaders headers, 
            @Context final UriInfo uriInfo,
            @Context final javax.ws.rs.core.Request req,
            final InputStream body) throws Exception {
		return request.buildRequest(Naming.Security.SECURITY_MANAGEMENT, jwt, headers, uriInfo, req, body);
    }
}
