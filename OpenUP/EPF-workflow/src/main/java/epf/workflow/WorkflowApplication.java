package epf.workflow;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.JsonValue;
import javax.validation.Valid;
import javax.ws.rs.BadRequestException;
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
import epf.workflow.event.Event;
import epf.workflow.schema.StartDefinition;
import epf.workflow.schema.WorkflowDefinition;

/**
 * @author PC
 *
 */
@ApplicationScoped
@Path(Naming.WORKFLOW)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class WorkflowApplication {
	
	/**
	 * 
	 */
	@Inject
	transient WorkflowRuntime runtime;
	
	/**
	 * 
	 */
	@Inject
	transient WorkflowPersistence persistence;
	
	/**
	 * 
	 */
	@Inject
	transient WorkflowSchedule schedule;

	/**
	 * @param workflowDefinition
	 * @return
	 */
	@POST
	public WorkflowDefinition newWorkflowDefinition(@Valid final WorkflowDefinition workflowDefinition) {
		WorkflowDefinition newWorkflowDefinition = persistence.persist(workflowDefinition);
		if(newWorkflowDefinition.getStart() instanceof StartDefinition) {
			schedule.schedule(newWorkflowDefinition);
		}
		return persistence.find(newWorkflowDefinition.getId());
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
			workflowDefinition = persistence.find(workflowId, version);
		}
		else {
			workflowDefinition = persistence.find(workflowId);
		}
		if(workflowDefinition != null) {
			if(workflowDefinition.getStart() instanceof StartDefinition) {
				throw new BadRequestException();
			}
			else {
				runtime.start(workflowDefinition, workflowData);
				return workflowData.getOutput();
			}
		}
		throw new NotFoundException();
	}
	
	@POST
	@Path("{workflowId}/event")
	public JsonValue consume(
			@PathParam("workflowId") 
			final String workflowId, 
			@Valid
			final Event event) throws Exception {
		final WorkflowDefinition workflowDefinition = persistence.find(workflowId);
		if(workflowDefinition != null) {
			if(workflowDefinition.getStart() instanceof StartDefinition) {
				throw new BadRequestException();
			}
			else {
				runtime.consume(event);
			}
		}
		throw new NotFoundException();
	}
}
