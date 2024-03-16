package epf.gateway.image;

import java.io.InputStream;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import jakarta.ws.rs.core.UriInfo;
import epf.gateway.Application;
import epf.naming.Naming;
import io.smallrye.common.annotation.Blocking;
import io.smallrye.common.annotation.RunOnVirtualThread;

/**
 *
 * @author FOXCONN
 */
@Path(Naming.IMAGE)
@ApplicationScoped
public class Image {
    
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
    @Path("contours")
	@POST
	@Consumes(MediaType.APPLICATION_OCTET_STREAM)
	@Produces(MediaType.APPLICATION_JSON)
    @RunOnVirtualThread
    @Blocking
    public Response findContours(
    		@Context final SecurityContext context,
            @Context final HttpHeaders headers, 
            @Context final UriInfo uriInfo,
            @Context final jakarta.ws.rs.core.Request req,
            final InputStream body
    ) throws Exception {
    	return request.buildRequest(Naming.IMAGE, null, headers, uriInfo, req, body);
    }
}
