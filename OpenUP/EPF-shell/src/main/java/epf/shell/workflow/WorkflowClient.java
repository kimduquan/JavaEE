package epf.shell.workflow;

import javax.ws.rs.Path;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import epf.naming.Naming;
import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * 
 */
@Path(Naming.WORKFLOW)
@RegisterRestClient(configKey = Naming.Client.CLIENT_CONFIG)
public interface WorkflowClient {
	
	/**
	 * @param token
	 * @param workflow
	 * @param version
	 * @param input
	 * @return
	 * @throws Exception
	 */
	@POST
	@Path("{workflow}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response start(
			@HeaderParam(HttpHeaders.AUTHORIZATION)
    		final String token,
			@PathParam("workflow")
			final String workflow, 
			@QueryParam("version")
			final String version,
			final String input) throws Exception;
}
