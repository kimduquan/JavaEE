package epf.workflow.util;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Objects;
import javax.el.ELProcessor;
import javax.json.Json;
import javax.json.JsonMergePatch;
import javax.json.JsonValue;
import epf.workflow.ScheduleTrigger;
import epf.workflow.WorkflowData;
import epf.workflow.el.JsonELResolver;
import epf.workflow.schema.CallbackState;
import epf.workflow.schema.EventDefinition;
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
public interface WorkflowUtil {
	
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
		WorkflowTimeoutDefinition workflowTimeoutDefinition = getTimeouts(null, workflowDefinition.getTimeouts());
		switch(state.getType()) {
			case Event:
				final EventState eventState = (EventState)state;
				workflowTimeoutDefinition = getTimeouts(workflowTimeoutDefinition, eventState.getTimeouts());
				break;
			case Operation:
				final OperationState operationState = (OperationState) state;
				workflowTimeoutDefinition = getTimeouts(workflowTimeoutDefinition, operationState.getTimeouts());
				break;
			case Switch:
				final SwitchState switchState = (SwitchState) state;
				workflowTimeoutDefinition = getTimeouts(workflowTimeoutDefinition, switchState.getTimeouts());
				break;
			case Sleep:
				break;
			case Parallel:
				final ParallelState parallelState = (ParallelState) state;
				workflowTimeoutDefinition = getTimeouts(workflowTimeoutDefinition, parallelState.getTimeouts());
				break;
			case Inject:
				break;
			case ForEach:
				final ForEachState forEachState = (ForEachState) state;
				workflowTimeoutDefinition = getTimeouts(workflowTimeoutDefinition, forEachState.getTimeouts());
				break;
			case Callback:
				final CallbackState callbackState = (CallbackState) state;
				workflowTimeoutDefinition = getTimeouts(workflowTimeoutDefinition, callbackState.getTimeouts());
				break;
			default:
				break;
		}
		return workflowTimeoutDefinition;
	}
	
	/**
	 * @param workflowDefinition
	 * @param eventRef
	 * @return
	 */
	static EventDefinition getEventDefinition(final WorkflowDefinition workflowDefinition, final String eventRef) {
		if(workflowDefinition.getEvents() instanceof EventDefinition[]) {
			return Arrays.asList((EventDefinition[])workflowDefinition.getEvents()).stream().filter(eventDef -> eventDef.getName().equals(eventRef)).findFirst().get();
		}
		return null;
	}
	
	/**
	 * @param filter
	 * @param object
	 * @return
	 */
	static JsonValue getValue(final String filter, final JsonValue object) {
		final ELProcessor elProcessor = newELProcessor(object);
		return (JsonValue) elProcessor.getValue(filter, JsonValue.class);
	}
	
	/**
	 * @param data
	 * @param object
	 * @param value
	 */
	static void setValue(final String data, final JsonValue object, final JsonValue value) {
		final ELProcessor elProcessor = newELProcessor(object);
		elProcessor.setValue(data, value);
	}
	
	/**
	 * @param data
	 * @return
	 */
	static ELProcessor newELProcessor(final JsonValue data) {
		final ELProcessor elProcessor = new ELProcessor();
		final JsonELResolver elResolver = new JsonELResolver(data);
		elResolver.defineBean(elProcessor);
		elProcessor.getELManager().addELResolver(elResolver);
		return elProcessor;
	}
	
	/**
	 * @param data
	 * @param condition
	 * @return
	 */
	static Boolean evaluateCondition(final JsonValue data, final String condition) {
		Objects.requireNonNull(data, "JsonValue");
		Objects.requireNonNull(condition, "String");
		final ELProcessor elProcessor = newELProcessor(data);
		return (Boolean) elProcessor.eval(condition);
	}
	
	/**
	 * @param data
	 * @param to
	 * @param fromOutput
	 */
	static void mergeStateDataOutput(final String data, final WorkflowData to, final JsonValue fromOutput) {
		JsonValue output = to.getOutput();
		output = getValue(data, to.getOutput());
		final JsonValue newOutput = Json.createMergeDiff(output, fromOutput).apply(output);
		setValue(data, to.getOutput(), newOutput);
	}
	
	/**
	 * @param to
	 * @param fromOutput
	 */
	static void mergeStateDataOutput(final WorkflowData to, final JsonValue fromOutput) {
		JsonValue output = to.getOutput();
		final JsonValue newOutput = Json.createMergeDiff(output, fromOutput).apply(output);
		to.setOutput(newOutput);
	}
	
	/**
	 * @param data
	 * @param to
	 * @param fromInput
	 */
	static void mergeStateDataInput(final String data, final WorkflowData to, final JsonValue fromInput) {
		JsonValue input = to.getInput();
		input = getValue(data, to.getInput());
		final JsonValue newInput = Json.createMergeDiff(input, fromInput).apply(input);
		setValue(data, to.getInput(), newInput);
	}
	
	/**
	 * @param to
	 * @param fromInput
	 */
	static void mergeStateDataInput(final WorkflowData to, final JsonValue fromInput) {
		JsonValue input = to.getInput();
		final JsonValue newInput = Json.createMergeDiff(input, fromInput).apply(input);
		to.setInput(newInput);
	}
	
	/**
	 * @param interval
	 * @return
	 */
	static ScheduleTrigger parseInterval(final String interval) {
		final String[] values = interval.split("/");
		Instant start = null;
		Duration duration = null;
		Instant end = null;
		if(values.length > 0 && values[0].equals("R")) {
			if(values.length > 1) {
				try {
					duration = Duration.parse(values[1]);
				}
				catch(Exception ex) {
					start = Instant.parse(values[1]);
				}
			}
			if(values.length > 2) {
				end = Instant.parse(values[2]);
			}
		}
		return new ScheduleTrigger(start, duration, end);
	}
}
