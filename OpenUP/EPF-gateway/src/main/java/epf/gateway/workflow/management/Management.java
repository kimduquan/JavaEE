package epf.gateway.workflow.management;

import java.io.InputStream;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import org.eclipse.microprofile.jwt.JsonWebToken;
import epf.gateway.Application;
import epf.naming.Naming;
import io.smallrye.common.annotation.Blocking;
import io.smallrye.common.annotation.RunOnVirtualThread;

/**
 * 
 */
@Path(Naming.Workflow.WORKFLOW_MANAGEMENT)
@ApplicationScoped
@RolesAllowed(Naming.Security.DEFAULT_ROLE)
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
    @POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
    @RunOnVirtualThread
    @Blocking
    public Response newWorkflowDefinition(
    		@Context final SecurityContext context,
            @Context final HttpHeaders headers, 
            @Context final UriInfo uriInfo,
            @Context final Request req,
            final InputStream body) throws Exception {
    	return request.buildRequest(Naming.Workflow.WORKFLOW_MANAGEMENT, jwt, headers, uriInfo, req, body);
    }
    
    /**
     * @param context
     * @param headers
     * @param uriInfo
     * @param req
     * @param workflow
     * @return
     * @throws Exception
     */
    @GET
	@Path("{workflow}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
    @RunOnVirtualThread
    @Blocking
    public Response getWorkflowDefinition(
			@Context final SecurityContext context,
            @Context final HttpHeaders headers, 
            @Context final UriInfo uriInfo,
            @Context final Request req,
			@PathParam(Naming.WORKFLOW) 
			final String workflow
			) throws Exception {
    	return request.buildRequest(Naming.Workflow.WORKFLOW_MANAGEMENT, jwt, headers, uriInfo, req, null);
    }
}
