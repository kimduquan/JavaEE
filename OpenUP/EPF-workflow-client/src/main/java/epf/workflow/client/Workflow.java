package epf.workflow.client;

import javax.json.JsonValue;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import epf.client.util.Client;
import epf.naming.Naming;
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
	 * @param input
	 * @return
	 */
	@PUT
	@Path("{workflow}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response start(
			@PathParam(Naming.WORKFLOW) 
			final String workflow, 
			@MatrixParam(VERSION)
			final String version,
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
			@MatrixParam(VERSION)
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
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response transition(
			@PathParam(STATE)
			final String state, 
			final JsonValue input) throws Exception;
	
	/**
	 * @param workflow
	 * @param state
	 * @return
	 */
	static Link transitionLink(final String workflow, final String state) {
		return Link.fromUri(String.format("/%s/%s/", workflow, state)).rel(Naming.WORKFLOW).type(HttpMethod.PUT).build();
	}
}
