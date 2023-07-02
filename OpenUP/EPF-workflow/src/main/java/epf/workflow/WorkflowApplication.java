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
import org.eclipse.microprofile.lra.annotation.ws.rs.LRA;
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
	
	private Response transitionLink(final String workflow, final String version, final String state, final URI instance, final WorkflowData workflowData) {
		return Response.ok(workflowData)
				.links(epf.workflow.client.Workflow.transitionLink(workflow, version, state))
				.header(LRA.LRA_HTTP_CONTEXT_HEADER, instance)
				.build();
	}

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
				String startState = null;
				if(workflowDefinition.getStart() != null) {
					if(workflowDefinition.getStart() instanceof String) {
						startState = (String)workflowDefinition.getStart();
					}
					else if(workflowDefinition.getStart() instanceof StartDefinition) {
						final StartDefinition startDef = (StartDefinition) workflowDefinition.getStart();
						startState = startDef.getStateName();
					}
				}
				else {
					startState = workflowDefinition.getStates()[0].getName();
				}
				final WorkflowData workflowData = new WorkflowData();
				workflowData.setInput(input);
				workflowData.setOutput(JsonUtil.empty());
				return transitionLink(workflow, version, startState, instance, workflowData);
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
	public Response transition(final String workflow, final String version, final String state, final URI instance, final JsonValue input) throws Exception {
		WorkflowDefinition workflowDefinition = getWorkflowDefinition(workflow, version);
		return null;
	}

	@Override
	public Response end(final String workflow, final String version, final String state, final URI instance, final JsonValue data) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response compensate(final String workflow, final String version, final String state, final URI instance, final JsonValue data) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}
