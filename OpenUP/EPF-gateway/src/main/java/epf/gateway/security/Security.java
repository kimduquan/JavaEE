package epf.gateway.security;

import java.io.InputStream;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
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
import epf.gateway.internal.ResponseUtil;
import epf.naming.Naming;

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
    public Response login(
    		@Context final SecurityContext context,
            @Context final HttpHeaders headers, 
            @Context final UriInfo uriInfo,
            @Context final javax.ws.rs.core.Request req,
            final InputStream body) throws Exception {
    	return ResponseUtil.buildResponse(request.buildRequest(Naming.SECURITY, null, headers, uriInfo, req, body).invoke(), uriInfo.getBaseUri());
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
    public Response logOut(
    		@Context final SecurityContext context,
            @Context final HttpHeaders headers, 
            @Context final UriInfo uriInfo,
            @Context final javax.ws.rs.core.Request req
            ) throws Exception {
    	return ResponseUtil.buildResponse(request.buildRequest(Naming.SECURITY, jwt, headers, uriInfo, req, null).invoke(), uriInfo.getBaseUri());
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
    public Response authenticate(
    		@Context final SecurityContext context,
            @Context final HttpHeaders headers, 
            @Context final UriInfo uriInfo,
            @Context final javax.ws.rs.core.Request req) throws Exception {
    	return ResponseUtil.buildResponse(request.buildRequest(Naming.SECURITY, jwt, headers, uriInfo, req, null).invoke(), uriInfo.getBaseUri());
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
    public Response update(
    		@Context final SecurityContext context,
            @Context final HttpHeaders headers, 
            @Context final UriInfo uriInfo,
            @Context final javax.ws.rs.core.Request req,
            final InputStream body) throws Exception {
    	return ResponseUtil.buildResponse(request.buildRequest(Naming.SECURITY, jwt, headers, uriInfo, req, body).invoke(), uriInfo.getBaseUri());
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
    public Response revoke(
    		@Context final SecurityContext context,
            @Context final HttpHeaders headers, 
            @Context final UriInfo uriInfo,
            @Context final javax.ws.rs.core.Request req) throws Exception {
    	return ResponseUtil.buildResponse(request.buildRequest(Naming.SECURITY, jwt, headers, uriInfo, req, null).invoke(), uriInfo.getBaseUri());
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
    public Response authenticateIDToken(
    		@Context final SecurityContext context,
            @Context final HttpHeaders headers, 
            @Context final UriInfo uriInfo,
            @Context final javax.ws.rs.core.Request req,
            final InputStream body) throws Exception {
    	return ResponseUtil.buildResponse(request.buildRequest(Naming.SECURITY, null, headers, uriInfo, req, body).invoke(), uriInfo.getBaseUri());
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
    public Response createPrincipal(
    		@Context final SecurityContext context,
            @Context final HttpHeaders headers, 
            @Context final UriInfo uriInfo,
            @Context final javax.ws.rs.core.Request req,
            final InputStream body) throws Exception {
    	return ResponseUtil.buildResponse(request.buildRequest(Naming.SECURITY, jwt, headers, uriInfo, req, body).invoke(), uriInfo.getBaseUri());
    }
}
