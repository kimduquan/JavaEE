package epf.workflow;

import java.net.URI;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.JsonValue;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import epf.naming.Naming;
import epf.util.json.JsonUtil;
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
public class WorkflowApplication implements epf.workflow.client.Workflow {
	
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

	@Override
	public Response newWorkflowDefinition(final WorkflowDefinition workflowDefinition) throws Exception {
		WorkflowDefinition newWorkflowDefinition = persistence.persist(workflowDefinition);
		if(newWorkflowDefinition.getStart() instanceof StartDefinition) {
			schedule.schedule(newWorkflowDefinition, null);
		}
		newWorkflowDefinition = persistence.find(newWorkflowDefinition.getId());
		return Response.ok(newWorkflowDefinition).build();
	}

	@Override
	public Response start(final String workflow, final String version, final URI instance, final JsonValue input) throws Exception {
		final WorkflowData workflowData = new WorkflowData();
		workflowData.setInput(input);
		workflowData.setOutput(JsonUtil.empty());
		WorkflowDefinition workflowDefinition = null;
		if(version != null) {
			workflowDefinition = persistence.find(workflow, version);
		}
		else {
			workflowDefinition = persistence.find(workflow);
		}
		if(workflowDefinition != null) {
			if(workflowDefinition.getStart() instanceof StartDefinition) {
				throw new BadRequestException();
			}
			else {
				runtime.start(workflowDefinition, workflowData, instance);
				final JsonValue output = workflowData.getOutput();
				return Response.ok(output).build();
			}
		}
		throw new NotFoundException();
	}

	@Override
	public WorkflowDefinition getWorkflowDefinition(final String workflow, final String version) throws Exception {
		WorkflowDefinition workflowDefinition = null;
		if(version != null) {
			workflowDefinition = persistence.find(workflow, version);
		}
		else {
			workflowDefinition = persistence.find(workflow);
		}
		if(workflowDefinition != null) {
			return workflowDefinition;
		}
		throw new NotFoundException();
	}

	@Override
	public Response transition(final String state, final URI instance, final JsonValue input) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response end(final String workflow, final String state, final URI instance, final JsonValue data) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response compensate(final String workflow, final String state, final URI instance, final JsonValue data) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}
