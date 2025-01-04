package epf.gateway.lang.messaging;

import java.io.InputStream;
import org.eclipse.microprofile.jwt.JsonWebToken;
import epf.gateway.Application;
import epf.naming.Naming;
import io.smallrye.common.annotation.RunOnVirtualThread;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

@ApplicationScoped
@Path("lang/messaging/messenger")
public class Messenger {

	@Inject
    transient Application request;
    
    @Inject
    transient JsonWebToken jwt;
    
    @GET
    @RunOnVirtualThread
	public Response verify(
			 	@Context final HttpHeaders headers, 
	            @Context final UriInfo uriInfo,
	            @Context final jakarta.ws.rs.core.Request req
			) throws Exception {
    	return request.buildRequest(Naming.LANG, null, headers, uriInfo, req, null);
    }
    
    @POST
	@Consumes(MediaType.APPLICATION_JSON)
    @RunOnVirtualThread
	public Response subscribe(
			@Context final HttpHeaders headers, 
            @Context final UriInfo uriInfo,
            @Context final jakarta.ws.rs.core.Request req,
            final InputStream body
			) throws Exception {
    	return request.buildRequest(Naming.LANG, null, headers, uriInfo, req, body);
    }
}
