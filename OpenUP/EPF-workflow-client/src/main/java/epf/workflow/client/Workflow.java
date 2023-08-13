package epf.workflow.client;

import java.net.URI;
import javax.json.JsonValue;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.eclipse.microprofile.lra.annotation.Compensate;
import org.eclipse.microprofile.lra.annotation.ws.rs.LRA;
import org.eclipse.microprofile.lra.annotation.ws.rs.LRA.Type;
import epf.client.util.Client;
import epf.naming.Naming;
import epf.workflow.schema.WorkflowData;
import epf.workflow.schema.WorkflowDefinition;

/**
 * 
 */
@Path(Naming.WORKFLOW)
public interface Workflow {
	
	/**
	 * 
	 */
	String VERSION = "version";
	
	/**
	 * 
	 */
	String STATE = "state";

	/**
	 * @param workflowDefinition
	 * @return
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response newWorkflowDefinition(final WorkflowDefinition workflowDefinition) throws Exception;
	
	/**
	 * @param client
	 * @param workflowDefinition
	 * @return
	 */
	static Response newWorkflowDefinition(final Client client, final WorkflowDefinition workflowDefinition) {
		return client.request(
    			target -> target, 
    			req -> req.accept(MediaType.APPLICATION_JSON)
    			)
    			.post(Entity.json(workflowDefinition));
	}
	
	/**
	 * @param workflow
	 * @param version
	 * @param instance
	 * @param input
	 * @return
	 * @throws Exception
	 */
	@PUT
	@Path("{workflow}")
	@LRA(value = Type.NESTED, end = false)
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response start(
			@PathParam(Naming.WORKFLOW) 
			final String workflow, 
			@QueryParam(VERSION)
			final String version,
			@HeaderParam(LRA.LRA_HTTP_CONTEXT_HEADER) 
			final URI instance,
			final JsonValue input
			) throws Exception;
	
	/**
	 * @param client
	 * @param workflow
	 * @param version
	 * @param input
	 * @return
	 */
	static Response start(final Client client, final String workflow, final String version, final JsonValue input) {
		return client.request(
				target -> target.path(workflow).matrixParam(VERSION, version), 
				req -> req.accept(MediaType.APPLICATION_JSON))
				.put(Entity.json(input));
	}
	
	/**
	 * @param workflow
	 * @param version
	 * @return
	 */
	static Link startLink(final String workflow, final String version) {
		if(version != null) {
			return Link.fromUri(String.format("/%s;version=%s/", workflow, version)).rel(Naming.WORKFLOW).type(HttpMethod.PUT).build();
		}
		return Link.fromUri(String.format("/%s/", workflow)).rel(Naming.WORKFLOW).type(HttpMethod.PUT).build();
	}
	
	/**
	 * @param workflow
	 * @param version
	 * @return
	 */
	@GET
	@Path("{workflow}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	WorkflowDefinition getWorkflowDefinition(
			@PathParam(Naming.WORKFLOW) 
			final String workflow, 
			@QueryParam(VERSION)
			final String version
			) throws Exception;
	
	/**
	 * @param client
	 * @param workflow
	 * @param version
	 * @return
	 */
	static Response getWorkflowDefinition(final Client client, final String workflow, final String version) {
		return client.request(
				target -> target.path(workflow).matrixParam(VERSION, version), 
				req -> req.accept(MediaType.APPLICATION_JSON))
				.get();
	}
	
	/**
	 * @param workflow
	 * @param version
	 * @return
	 */
	static Link getWorkflowDefinitionLink(final String workflow, final String version) {
		if(version != null) {
			return Link.fromUri(String.format("/%s;version=%s/", workflow, version)).rel(Naming.WORKFLOW).type(HttpMethod.GET).build();
		}
		return Link.fromUri(String.format("/%s/", workflow)).rel(Naming.WORKFLOW).type(HttpMethod.GET).build();
	}
	
	/**
	 * @param input
	 * @return
	 */
	@PUT
	@Path("{workflow}/{state}")
	@LRA(value = Type.MANDATORY, end = false)
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response transition(
			@PathParam(Naming.WORKFLOW) 
			final String workflow,
			@QueryParam(VERSION)
			final String version,
			@PathParam(STATE)
			final String state,
			@HeaderParam(LRA.LRA_HTTP_CONTEXT_HEADER) 
			final URI instance,
			final WorkflowData workflowData) throws Exception;
	
	/**
	 * @param workflow
	 * @param version
	 * @param state
	 * @return
	 */
	static Link transitionLink(final String workflow, final String version, final String state) {
		if(version != null) {
			return Link.fromUri(String.format("/%s;version=%s/%s/", workflow, version, state)).rel(Naming.WORKFLOW).type(HttpMethod.PUT).build();
		}
		return Link.fromUri(String.format("/%s/%s/", workflow, state)).rel(Naming.WORKFLOW).type(HttpMethod.PUT).build();
	}
	
	/**
	 * @param workflow
	 * @param state
	 * @param instance
	 * @param data
	 * @return
	 * @throws Exception
	 */
	@PUT
	@Path("{workflow}/{state}/end")
	@LRA(value = Type.MANDATORY, end = true)
	@Consumes(MediaType.APPLICATION_JSON)
	Response end(
			@PathParam(Naming.WORKFLOW) 
			final String workflow,
			@QueryParam(VERSION)
			final String version,
			@PathParam(STATE)
			final String state,
			@HeaderParam(LRA.LRA_HTTP_CONTEXT_HEADER) 
			final URI instance,
			final WorkflowData workflowData) throws Exception;
	
	/**
	 * @param workflow
	 * @param version
	 * @param state
	 * @return
	 */
	static Link endLink(final String workflow, final String version, final String state) {
		if(version != null) {
			return Link.fromUri(String.format("/%s;version=%s/%s/end/", workflow, version, state)).rel(Naming.WORKFLOW).type(HttpMethod.PUT).build();
		}
		return Link.fromUri(String.format("/%s/%s/end/", workflow, state)).rel(Naming.WORKFLOW).type(HttpMethod.PUT).build();
	}
	
	/**
	 * @param workflow
	 * @param state
	 * @param instance
	 * @param data
	 * @return
	 * @throws Exception
	 */
	@PUT
	@Path("{workflow}/{state}/compensate")
	@Consumes(MediaType.APPLICATION_JSON)
	@Compensate
	Response compensate(
			@PathParam(Naming.WORKFLOW) 
			final String workflow,
			@QueryParam(VERSION)
			final String version,
			@PathParam(STATE)
			final String state,
			@HeaderParam(LRA.LRA_HTTP_CONTEXT_HEADER) 
			final URI instance,
			final WorkflowData workflowData
			) throws Exception;
	
	/**
	 * @param workflow
	 * @param version
	 * @param state
	 * @return
	 */
	static Link compensateLink(final String workflow, final String version, final String state) {
		if(version != null) {
			return Link.fromUri(String.format("/%s;version=%s/%s/compensate/", workflow, version, state)).rel(Naming.WORKFLOW).type(HttpMethod.PUT).build();
		}
		return Link.fromUri(String.format("/%s/%s/compensate/", workflow, state)).rel(Naming.WORKFLOW).type(HttpMethod.PUT).build();
	}
}
