package epf.gateway.script;

import java.io.InputStream;
import java.util.concurrent.CompletionStage;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import epf.gateway.Application;
import epf.gateway.internal.ResponseUtil;
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
    transient Application request;
	
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
    public CompletionStage<Response> eval(
    		@Context final SecurityContext context,
            @Context final HttpHeaders headers, 
            @Context final UriInfo uriInfo,
            @Context final javax.ws.rs.core.Request req,
            final InputStream body
    ) throws Exception {
		final Client client = ClientBuilder.newClient();
    	return ResponseUtil.buildResponse(client, request.buildRequest(client, Naming.SCRIPT, null, headers, uriInfo, req, body), uriInfo.getBaseUri());
    }
}
