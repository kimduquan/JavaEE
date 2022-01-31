package epf.gateway.security;

import java.io.InputStream;
import java.util.concurrent.CompletionStage;
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
import epf.gateway.Application;
import epf.naming.Naming;
import io.smallrye.common.annotation.Blocking;

/**
 *
 * @author FOXCONN
 */
@Blocking
@Path(Naming.SECURITY)
@ApplicationScoped
@RolesAllowed(Naming.Security.DEFAULT_ROLE)
public class Security {
    
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
    @PermitAll
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    public CompletionStage<Response> login(
    		@Context final SecurityContext context,
            @Context final HttpHeaders headers, 
            @Context final UriInfo uriInfo,
            @Context final javax.ws.rs.core.Request req,
            final InputStream body) throws Exception {
        return request.request(Naming.SECURITY, context, headers, uriInfo, req, body);
    }
    
    /**
     * @param headers
     * @param uriInfo
     * @param req
     * @return
     * @throws Exception 
     */
    @DELETE
    @Produces(MediaType.TEXT_PLAIN)
    public CompletionStage<Response> logOut(
    		@Context final SecurityContext context,
            @Context final HttpHeaders headers, 
            @Context final UriInfo uriInfo,
            @Context final javax.ws.rs.core.Request req
            ) throws Exception {
        return request.request(Naming.SECURITY, context, headers, uriInfo, req, null);
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
    public CompletionStage<Response> authenticate(
    		@Context final SecurityContext context,
            @Context final HttpHeaders headers, 
            @Context final UriInfo uriInfo,
            @Context final javax.ws.rs.core.Request req) throws Exception {
        return request.request(Naming.SECURITY, context, headers, uriInfo, req, null);
    }
    
    /**
     * @param headers
     * @param uriInfo
     * @param req
     * @param body
     * @return
     * @throws Exception 
     */
    @PATCH
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public CompletionStage<Response> update(
    		@Context final SecurityContext context,
            @Context final HttpHeaders headers, 
            @Context final UriInfo uriInfo,
            @Context final javax.ws.rs.core.Request req,
            final InputStream body) throws Exception {
    	return request.request(Naming.SECURITY, context, headers, uriInfo, req, body);
    }
    
    /**
     * @param headers
     * @param uriInfo
     * @param req
     * @param body
     * @return
     * @throws Exception 
     */
    @PUT
    @Produces(MediaType.TEXT_PLAIN)
    public CompletionStage<Response> revoke(
    		@Context final SecurityContext context,
            @Context final HttpHeaders headers, 
            @Context final UriInfo uriInfo,
            @Context final javax.ws.rs.core.Request req) throws Exception {
    	return request.request(Naming.SECURITY, context, headers, uriInfo, req, null);
    }
}
