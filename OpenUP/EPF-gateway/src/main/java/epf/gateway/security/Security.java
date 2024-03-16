package epf.gateway.security;

import java.io.InputStream;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
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
 * @author FOXCONN
 */
@Path(Naming.SECURITY)
@ApplicationScoped
public class Security {
    
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
    @PermitAll
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    @RunOnVirtualThread
    @Blocking
    public Response login(
    		@Context final SecurityContext context,
            @Context final HttpHeaders headers, 
            @Context final UriInfo uriInfo,
            @Context final jakarta.ws.rs.core.Request req,
            final InputStream body) throws Exception {
		return request.buildRequest(Naming.SECURITY, null, headers, uriInfo, req, body);
    }
    
    /**
     * @param headers
     * @param uriInfo
     * @param req
     * @return
     * @throws Exception 
     */
    @RolesAllowed(Naming.Security.DEFAULT_ROLE)
    @DELETE
    @Produces(MediaType.TEXT_PLAIN)
    @RunOnVirtualThread
    @Blocking
    public Response logOut(
    		@Context final SecurityContext context,
            @Context final HttpHeaders headers, 
            @Context final UriInfo uriInfo,
            @Context final jakarta.ws.rs.core.Request req
            ) throws Exception {
		return request.buildRequest(Naming.SECURITY, jwt, headers, uriInfo, req, null);
    }
    
    /**
     * @param headers
     * @param uriInfo
     * @param req
     * @return
     * @throws Exception 
     */
    @RolesAllowed(Naming.Security.DEFAULT_ROLE)
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @RunOnVirtualThread
    @Blocking
    public Response authenticate(
    		@Context final SecurityContext context,
            @Context final HttpHeaders headers, 
            @Context final UriInfo uriInfo,
            @Context final jakarta.ws.rs.core.Request req) throws Exception {
		return request.buildRequest(Naming.SECURITY, jwt, headers, uriInfo, req, null);
    }
    
    /**
     * @param headers
     * @param uriInfo
     * @param req
     * @param body
     * @return
     * @throws Exception 
     */
    @RolesAllowed(Naming.Security.DEFAULT_ROLE)
    @PATCH
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @RunOnVirtualThread
    @Blocking
    public Response update(
    		@Context final SecurityContext context,
            @Context final HttpHeaders headers, 
            @Context final UriInfo uriInfo,
            @Context final jakarta.ws.rs.core.Request req,
            final InputStream body) throws Exception {
		return request.buildRequest(Naming.SECURITY, jwt, headers, uriInfo, req, body);
    }
    
    /**
     * @param headers
     * @param uriInfo
     * @param req
     * @param body
     * @return
     * @throws Exception 
     */
    @RolesAllowed(Naming.Security.DEFAULT_ROLE)
    @PUT
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    @RunOnVirtualThread
    @Blocking
    public Response revoke(
    		@Context final SecurityContext context,
            @Context final HttpHeaders headers, 
            @Context final UriInfo uriInfo,
            @Context final jakarta.ws.rs.core.Request req) throws Exception {
		return request.buildRequest(Naming.SECURITY, jwt, headers, uriInfo, req, null);
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
    @Path(Naming.Security.AUTH)
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @RunOnVirtualThread
    @Blocking
    public Response authenticateIDToken(
    		@Context final SecurityContext context,
            @Context final HttpHeaders headers, 
            @Context final UriInfo uriInfo,
            @Context final jakarta.ws.rs.core.Request req,
            final InputStream body) throws Exception {
		return request.buildRequest(Naming.SECURITY, null, headers, uriInfo, req, body);
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
    @Path(Naming.Security.PRINCIPAL)
    @POST
    @RunOnVirtualThread
    @Blocking
    public Response createPrincipal(
    		@Context final SecurityContext context,
            @Context final HttpHeaders headers, 
            @Context final UriInfo uriInfo,
            @Context final jakarta.ws.rs.core.Request req,
            final InputStream body) throws Exception {
		return request.buildRequest(Naming.SECURITY, jwt, headers, uriInfo, req, body);
    }
}
