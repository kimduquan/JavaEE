package epf.workflow;

import java.util.Optional;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.JsonValue;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import epf.naming.Naming;
import epf.workflow.schema.WorkflowDefinition;
import jakarta.nosql.mapping.document.DocumentTemplate;

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
	DocumentTemplate document;

	/**
	 * @param workflowDefinition
	 * @return
	 */
	@POST
	public WorkflowDefinition newWorkflowDefinition(@Valid final WorkflowDefinition workflowDefinition) {
		return document.insert(workflowDefinition);
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
			final JsonValue input) throws Exception {
		final WorkflowData workflowData = new WorkflowData();
		final Optional<WorkflowDefinition> workflowDefinition = document.find(WorkflowDefinition.class, workflowId);
		if(workflowDefinition.isPresent()) {
			workflowRuntime.start(workflowDefinition.get(), workflowData);
			return workflowData.getOutput();
		}
		throw new NotFoundException();
	}
}
