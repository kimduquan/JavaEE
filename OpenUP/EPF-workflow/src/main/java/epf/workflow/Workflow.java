package epf.workflow;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.JsonValue;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import epf.naming.Naming;
import epf.util.json.JsonUtil;
import epf.workflow.schema.WorkflowDefinition;

/**
 * @author PC
 *
 */
@ApplicationScoped
@Path(Naming.WORKFLOW)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class Workflow {
	
	/**
	 * 
	 */
	@Inject
	WorkflowRuntime workflowRuntime;
	
	/**
	 * 
	 */
	@Inject
	WorkflowRepository workflowRepository;

	/**
	 * @param workflowDefinition
	 * @return
	 */
	@POST
	public WorkflowDefinition newWorkflowDefinition(@Valid final WorkflowDefinition workflowDefinition) {
		WorkflowDefinition newWorkflowDefinition = workflowRepository.persistWorkflowDefinition(workflowDefinition);
		return workflowRepository.getWorkflowDefinition(newWorkflowDefinition.getId());
	}
	
	/**
	 * @param workflowId
	 * @param input
	 * @return
	 * @throws Exception
	 */
	@POST
	@Path("{workflowId}")
	public JsonValue start(
			@PathParam("workflowId") 
			final String workflowId, 
			@MatrixParam("version")
			final String version,
			final JsonValue input) throws Exception {
		final WorkflowData workflowData = new WorkflowData();
		workflowData.setInput(input);
		workflowData.setOutput(JsonUtil.empty());
		WorkflowDefinition workflowDefinition = null;
		if(version != null) {
			workflowDefinition = workflowRepository.findWorkflowDefinition(workflowId, version);
		}
		else {
			workflowDefinition = workflowRepository.getWorkflowDefinition(workflowId);
		}
		if(workflowDefinition != null) {
			workflowRuntime.start(workflowDefinition, workflowData);
			return workflowData.getOutput();
		}
		throw new NotFoundException();
	}
}