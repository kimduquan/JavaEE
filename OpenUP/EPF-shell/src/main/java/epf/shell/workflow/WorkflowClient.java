package epf.shell.workflow;

import jakarta.ws.rs.Path;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import epf.naming.Naming;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

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
