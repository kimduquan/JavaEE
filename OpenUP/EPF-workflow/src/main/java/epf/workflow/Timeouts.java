package epf.workflow;

import java.time.Duration;

import epf.workflow.schema.CallbackState;
import epf.workflow.schema.EventState;
import epf.workflow.schema.ForEachState;
import epf.workflow.schema.OperationState;
import epf.workflow.schema.ParallelState;
import epf.workflow.schema.State;
import epf.workflow.schema.SwitchState;
import epf.workflow.schema.WorkflowDefinition;
import epf.workflow.schema.WorkflowTimeoutDefinition;

/**
 * @author PC
 *
 */
public interface Timeouts {
	
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
	static Duration getActionExecTimeout(final WorkflowDefinition WorkflowDefinition, final State state) {
		Duration actionExecTimeout = null;
		WorkflowTimeoutDefinition workflowTimeoutDefinition = getTimeouts(WorkflowDefinition, state);
		if(workflowTimeoutDefinition != null && workflowTimeoutDefinition.getActionExecTimeout() != null) {
			actionExecTimeout = Duration.parse(workflowTimeoutDefinition.getActionExecTimeout());
		}
		return actionExecTimeout;
	}
	
	/**
	 * @param workflowDefinition
	 * @param state
	 * @return
	 */
	static WorkflowTimeoutDefinition getTimeouts(final WorkflowDefinition workflowDefinition, final State state) {
		WorkflowTimeoutDefinition workflowTimeoutDefinition = Timeouts.getTimeouts(null, workflowDefinition.getTimeouts());
		switch(state.getType()) {
			case Event:
				final EventState eventState = (EventState)state;
				workflowTimeoutDefinition = Timeouts.getTimeouts(workflowTimeoutDefinition, eventState.getTimeouts());
				break;
			case Operation:
				final OperationState operationState = (OperationState) state;
				workflowTimeoutDefinition = Timeouts.getTimeouts(workflowTimeoutDefinition, operationState.getTimeouts());
				break;
			case Switch:
				final SwitchState switchState = (SwitchState) state;
				workflowTimeoutDefinition = Timeouts.getTimeouts(workflowTimeoutDefinition, switchState.getTimeouts());
				break;
			case Sleep:
				break;
			case Parallel:
				final ParallelState parallelState = (ParallelState) state;
				workflowTimeoutDefinition = Timeouts.getTimeouts(workflowTimeoutDefinition, parallelState.getTimeouts());
				break;
			case Inject:
				break;
			case ForEach:
				final ForEachState forEachState = (ForEachState) state;
				workflowTimeoutDefinition = Timeouts.getTimeouts(workflowTimeoutDefinition, forEachState.getTimeouts());
				break;
			case Callback:
				final CallbackState callbackState = (CallbackState) state;
				workflowTimeoutDefinition = Timeouts.getTimeouts(workflowTimeoutDefinition, callbackState.getTimeouts());
				break;
			default:
				break;
		}
		return workflowTimeoutDefinition;
	}
}
