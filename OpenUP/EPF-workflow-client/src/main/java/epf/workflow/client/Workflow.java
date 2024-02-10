package epf.workflow.client;

import java.io.InputStream;
import java.net.URI;
import org.eclipse.microprofile.lra.annotation.ws.rs.LRA;
import epf.naming.Naming;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.HeaderParam;
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
@Path(Naming.WORKFLOW)
public interface Workflow {

	/**
	 * @param workflow
	 * @param version
	 * @param instance
	 * @param body
	 * @return
	 * @throws Exception
	 */
	@POST
	@Path("{workflow}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response start(
			@PathParam("workflow")
			final String workflow, 
			@QueryParam("version")
			final String version, 
			@HeaderParam(LRA.LRA_HTTP_CONTEXT_HEADER)
			final URI instance, 
			final InputStream body) throws Exception;
}
