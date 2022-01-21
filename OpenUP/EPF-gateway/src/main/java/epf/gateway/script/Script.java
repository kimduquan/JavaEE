package epf.gateway.script;

import java.io.InputStream;
import java.util.concurrent.CompletionStage;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import org.eclipse.microprofile.faulttolerance.Asynchronous;
import epf.gateway.Request;
import epf.naming.Naming;

/**
 * @author PC
 *
 */
@Path(Naming.SCRIPT)
@ApplicationScoped
public class Script {

	/**
	 * 
	 */
	@Inject
    transient Request request;
	
	/**
	 * @param headers
	 * @param uriInfo
	 * @param req
	 * @param body
	 * @throws Exception 
	 */
	@POST
	@Consumes(MediaType.APPLICATION_OCTET_STREAM)
	@Produces(MediaType.APPLICATION_JSON)
	@Asynchronous
    public CompletionStage<Response> eval(
            @Context final HttpHeaders headers, 
            @Context final UriInfo uriInfo,
            @Context final javax.ws.rs.core.Request req,
            final InputStream body
    ) throws Exception {
        return request.request(headers, uriInfo, req, body);
    }
}
