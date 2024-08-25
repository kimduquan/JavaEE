package epf.shell.workflow.management;

import jakarta.ws.rs.Path;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import epf.naming.Naming;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * 
 */
@Path(Naming.Workflow.WORKFLOW_MANAGEMENT)
@RegisterRestClient(configKey = Naming.Client.CLIENT_CONFIG)
public interface ManagementClient {

	/**
	 * @param token
	 * @param input
	 * @return
	 * @throws Exception
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response newWorkflowDefinition(
			@HeaderParam(HttpHeaders.AUTHORIZATION)
    		final String token,
			final String input) throws Exception;
}
