package epf.gateway.workflow;

import java.io.InputStream;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
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
@Path(Naming.WORKFLOW)
@ApplicationScoped
@RolesAllowed(Naming.Security.DEFAULT_ROLE)
public class Workflow {
    
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
     * @param workflow
     * @param body
     * @return
     * @throws Exception
     */
    @POST
	@Path("{workflow}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
    @RunOnVirtualThread
    @Blocking
    public Response start(
    		@Context final SecurityContext context,
            @Context final HttpHeaders headers, 
            @Context final UriInfo uriInfo,
            @Context final Request req,
			@PathParam(Naming.WORKFLOW) 
			final String workflow,
			final InputStream body) throws Exception {
    	return request.buildRequest(Naming.WORKFLOW, jwt, headers, uriInfo, req, body);
    }
}
