package epf.workflow.client;

import java.io.InputStream;
import epf.naming.Naming;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * 
 */
@Path(Naming.Workflow.WORKFLOW_MANAGEMENT)
public interface Management {

	/**
	 * @param body
	 * @return
	 * @throws Exception
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response newWorkflowDefinition(final InputStream body) throws Exception;
	
	/**
	 * @param workflow
	 * @param version
	 * @return
	 * @throws Exception
	 */
	@GET
	@Path("{workflow}")
	@Produces(MediaType.APPLICATION_JSON)
	Response getWorkflowDefinition(
			@PathParam("workflow")
			final String workflow, 
			@QueryParam("version")
			final String version) throws Exception;
}
