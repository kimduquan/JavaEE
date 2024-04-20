package epf.workflow.data;

import epf.workflow.expressions.WorkflowExpressions;
import epf.workflow.schema.state.CallbackState;
import epf.workflow.schema.state.EventState;
import epf.workflow.schema.state.ForEachState;
import epf.workflow.schema.state.InjectState;
import epf.workflow.schema.state.OperationState;
import epf.workflow.schema.state.ParallelState;
import epf.workflow.schema.state.State;
import epf.workflow.schema.state.StateDataFilter;
import epf.workflow.schema.state.SwitchState;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.json.JsonValue;

/**
 * 
 */
@ApplicationScoped
public class StateDataFilters {
	
	/**
	 * 
	 */
	@Inject
	transient WorkflowExpressions workflowExpressions;
	
	/**
	 * @param state
	 * @return
	 */
	public StateDataFilter getStateDataFilter(final State state) {
		StateDataFilter stateDataFilter = null;
		switch(state.getType_()) {
			case Switch:
				final SwitchState switchState = (SwitchState) state;
				stateDataFilter = switchState.getStateDataFilter();
				break;
			case callback:
				final CallbackState callbackState = (CallbackState) state;
				stateDataFilter = callbackState.getStateDataFilter();
				break;
			case event:
				final EventState eventState = (EventState) state;
				stateDataFilter = eventState.getStateDataFilter();
				break;
			case foreach:
				final ForEachState forEachState = (ForEachState) state;
				stateDataFilter = forEachState.getStateDataFilter();
				break;
			case inject:
				final InjectState injectState = (InjectState) state;
				stateDataFilter = injectState.getStateDataFilter();
				break;
			case operation:
				final OperationState operationState = (OperationState) state;
				stateDataFilter = operationState.getStateDataFilter();
				break;
			case parallel:
				final ParallelState parallelState = (ParallelState) state;
				stateDataFilter = parallelState.getStateDataFilter();
				break;
			case sleep:
				break;
			default:
				break;
		}
		return stateDataFilter;
	}
	
	public JsonValue filterStateDataInput(final StateDataFilter stateDataFilter, final JsonValue input) throws Exception {
		if(stateDataFilter != null && stateDataFilter.getInput() != null) {
			return workflowExpressions.getValue(stateDataFilter.getInput(), input);
		}
		return input;
	}
	
	public JsonValue filterStateDataOutput(final StateDataFilter stateDataFilter, final JsonValue output) throws Exception {
		if(stateDataFilter != null && stateDataFilter.getOutput() != null) {
			return workflowExpressions.getValue(stateDataFilter.getOutput(), output);
		}
		return output;
	}
}
