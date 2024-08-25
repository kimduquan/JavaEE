package epf.workflow.compensation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import epf.workflow.client.Internal;
import epf.workflow.model.Instance;
import epf.workflow.model.WorkflowState;
import epf.workflow.schema.state.CallbackState;
import epf.workflow.schema.state.EventState;
import epf.workflow.schema.state.ForEachState;
import epf.workflow.schema.state.InjectState;
import epf.workflow.schema.state.OperationState;
import epf.workflow.schema.state.ParallelState;
import epf.workflow.schema.state.State;
import epf.workflow.schema.state.SwitchState;
import epf.workflow.util.LinkBuilder;
import epf.workflow.util.ResponseBuilder;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Link;

/**
 * 
 */
@ApplicationScoped
public class WorkflowCompensation {
	
	public Optional<String> getCompensatedBy(final State state) {
		String compensatedBy = null;
		switch(state.getType_()) {
			case event:
				final EventState eventState = (EventState)state;
				compensatedBy = eventState.getCompensatedBy();
				break;
			case operation:
				final OperationState operationState = (OperationState) state;
				compensatedBy = operationState.getCompensatedBy();
				break;
			case Switch:
				final SwitchState switchState = (SwitchState) state;
				compensatedBy = switchState.getCompensatedBy();
				break;
			case sleep:
				break;
			case parallel:
				final ParallelState parallelState = (ParallelState) state;
				compensatedBy = parallelState.getCompensatedBy();
				break;
			case inject:
				final InjectState injectState = (InjectState) state;
				compensatedBy = injectState.getCompensatedBy();
				break;
			case foreach:
				final ForEachState forEachState = (ForEachState) state;
				compensatedBy = forEachState.getCompensatedBy();
				break;
			case callback:
				final CallbackState callbackState = (CallbackState) state;
				compensatedBy = callbackState.getCompensatedBy();
				break;
			default:
				break;
		}
		return Optional.ofNullable(compensatedBy);
	}
	
	public ResponseBuilder compensates(final ResponseBuilder response, final String workflow, final Optional<String> version, final Instance workflowInstance) {
		final LinkBuilder builder = new LinkBuilder();
		final List<Link> compensateLinks = new ArrayList<>();
		WorkflowState workflowState = workflowInstance.getState();
		while(workflowState != null) {
			final Link compensateLink = Internal.compensateLink(workflow, version, workflowState.getName());
			final Link link = builder.link(compensateLink).at(response.getSize()).build();
			compensateLinks.add(link);
			workflowState = workflowState.getPreviousState();
		}
		return response.links(compensateLinks.toArray(new Link[0]));
	}
}
