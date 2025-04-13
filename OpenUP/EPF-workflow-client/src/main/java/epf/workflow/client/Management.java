package epf.workflow.client;

import java.io.InputStream;
import java.util.Optional;
import epf.naming.Naming;
import epf.workflow.client.util.LinkUtil;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HttpMethod;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Link;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;

@Path(Naming.Workflow.WORKFLOW_MANAGEMENT)
public interface Management {

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response newWorkflowDefinition(final InputStream body) throws Exception;
	
	@GET
	@Path("{workflow}")
	@Produces(MediaType.APPLICATION_JSON)
	Response getWorkflowDefinition(
			@PathParam("workflow")
			final String workflow, 
			@QueryParam("version")
			final String version) throws Exception;

	static Link getWorkflowLink(final int index, final String workflow, final Optional<String> version) {
		UriBuilder uri = UriBuilder.fromUri("{workflow}");
		if(version.isPresent()) {
			uri = uri.queryParam("version", version.get());
		}
		return LinkUtil.build(uri, index, Naming.Workflow.WORKFLOW_MANAGEMENT, HttpMethod.GET, workflow);
	}
}
