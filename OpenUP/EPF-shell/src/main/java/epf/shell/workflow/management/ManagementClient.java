package epf.shell.workflow.management;

import javax.ws.rs.Path;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import epf.naming.Naming;
import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
