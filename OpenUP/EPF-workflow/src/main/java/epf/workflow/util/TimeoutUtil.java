package epf.workflow.util;

import java.time.Duration;
import java.util.Optional;

import epf.workflow.schema.WorkflowDefinition;
import epf.workflow.schema.WorkflowTimeoutDefinition;
import epf.workflow.schema.state.CallbackState;
import epf.workflow.schema.state.EventState;
import epf.workflow.schema.state.ForEachState;
import epf.workflow.schema.state.OperationState;
import epf.workflow.schema.state.ParallelState;
import epf.workflow.schema.state.State;
import epf.workflow.schema.state.SwitchState;

/**
 * @author PC
 *
 */
public interface TimeoutUtil {
	
	/**
	 * @param workflowTimeoutDefinition
	 * @param timeouts
	 * @return
	 */
	static WorkflowTimeoutDefinition getTimeouts(WorkflowTimeoutDefinition workflowTimeoutDefinition, final Object timeouts) {
		if(timeouts instanceof WorkflowTimeoutDefinition) {
			if(workflowTimeoutDefinition == null) {
				workflowTimeoutDefinition = (WorkflowTimeoutDefinition) timeouts;
			}
			else {
				final WorkflowTimeoutDefinition newWorkflowTimeoutDefinition = (WorkflowTimeoutDefinition) timeouts;
				if(newWorkflowTimeoutDefinition.getActionExecTimeout() != null) {
					workflowTimeoutDefinition.setActionExecTimeout(newWorkflowTimeoutDefinition.getActionExecTimeout());
				}
				if(newWorkflowTimeoutDefinition.getBranchExecTimeout() != null) {
					workflowTimeoutDefinition.setBranchExecTimeout(newWorkflowTimeoutDefinition.getBranchExecTimeout());
				}
				if(newWorkflowTimeoutDefinition.getEventTimeout() != null) {
					workflowTimeoutDefinition.setEventTimeout(newWorkflowTimeoutDefinition.getEventTimeout());
				}
				if(newWorkflowTimeoutDefinition.getStateExecTimeout() != null) {
					workflowTimeoutDefinition.setStateExecTimeout(newWorkflowTimeoutDefinition.getStateExecTimeout());
				}
				if(newWorkflowTimeoutDefinition.getWorkflowExecTimeout() != null) {
					workflowTimeoutDefinition.setWorkflowExecTimeout(newWorkflowTimeoutDefinition.getWorkflowExecTimeout());
				}
			}
		}
		return workflowTimeoutDefinition;
	}
	
	/**
	 * @param WorkflowDefinition
	 * @param state
	 * @return
	 */
	static Optional<Duration> getActionExecTimeout(final WorkflowDefinition WorkflowDefinition, final State state) {
		Optional<Duration> actionExecTimeout = Optional.empty();
		WorkflowTimeoutDefinition workflowTimeoutDefinition = getTimeouts(WorkflowDefinition, state);
		if(workflowTimeoutDefinition != null && workflowTimeoutDefinition.getActionExecTimeout() != null) {
			actionExecTimeout = Optional.of(Duration.parse(workflowTimeoutDefinition.getActionExecTimeout()));
		}
		return actionExecTimeout;
	}
	
	/**
	 * @param workflowDefinition
	 * @param state
	 * @return
	 */
	static WorkflowTimeoutDefinition getTimeouts(final WorkflowDefinition workflowDefinition, final State state) {
		WorkflowTimeoutDefinition workflowTimeoutDefinition = getTimeouts(null, workflowDefinition.getTimeouts());
		switch(state.getType_()) {
			case event:
				final EventState eventState = (EventState)state;
				workflowTimeoutDefinition = getTimeouts(workflowTimeoutDefinition, eventState.getTimeouts());
				break;
			case operation:
				final OperationState operationState = (OperationState) state;
				workflowTimeoutDefinition = getTimeouts(workflowTimeoutDefinition, operationState.getTimeouts());
				break;
			case Switch:
				final SwitchState switchState = (SwitchState) state;
				workflowTimeoutDefinition = getTimeouts(workflowTimeoutDefinition, switchState.getTimeouts());
				break;
			case sleep:
				break;
			case parallel:
				final ParallelState parallelState = (ParallelState) state;
				workflowTimeoutDefinition = getTimeouts(workflowTimeoutDefinition, parallelState.getTimeouts());
				break;
			case inject:
				break;
			case foreach:
				final ForEachState forEachState = (ForEachState) state;
				workflowTimeoutDefinition = getTimeouts(workflowTimeoutDefinition, forEachState.getTimeouts());
				break;
			case callback:
				final CallbackState callbackState = (CallbackState) state;
				workflowTimeoutDefinition = getTimeouts(workflowTimeoutDefinition, callbackState.getTimeouts());
				break;
			default:
				break;
		}
		return workflowTimeoutDefinition;
	}
}
