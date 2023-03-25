package epf.webapp.workflow;

import javax.faces.context.FacesContext;
import javax.faces.flow.FlowHandler;
import javax.faces.flow.FlowHandlerFactory;
import javax.faces.flow.FlowHandlerFactoryWrapper;
import epf.webapp.workflow.flow.builder.WorkflowBuilder;
import epf.workflow.schema.Workflow;
import epf.workflow.schema.end.End;
import epf.workflow.schema.interfaces.State;
import epf.workflow.schema.states.CallbackState;
import epf.workflow.schema.states.EventState;
import epf.workflow.schema.states.ForEachState;
import epf.workflow.schema.states.InjectState;
import epf.workflow.schema.states.OperationState;
import epf.workflow.schema.states.ParallelState;
import epf.workflow.schema.states.SleepState;
import epf.workflow.schema.states.SwitchState;
import epf.workflow.schema.switchconditions.DataCondition;
import epf.workflow.schema.transitions.Transition;
import javax.faces.flow.builder.FlowBuilder;
import javax.faces.flow.builder.SwitchBuilder;
import javax.faces.flow.Flow;

/**
 * @author PC
 *
 */
public class WorkflowFactory extends FlowHandlerFactoryWrapper {
	
	/**
	 * 
	 */
	private static final String STATE_VDL_DOCUMENT_ID_FORMAT = "/workflow/state/%s.xhtml";

	/**
	 * @param wrapped
	 */
	public WorkflowFactory(final FlowHandlerFactory wrapped) {
		super(wrapped);
	}
	
	@Override
	public FlowHandler createFlowHandler(final FacesContext context) {
		final FlowHandler handler = super.createFlowHandler(context);
		final Workflow workflow = new Workflow();
		final Flow flow = build(workflow);
		handler.addFlow(context, flow);
		return handler;
	}
	
	private Flow build(final Workflow workflow) {
		final FlowBuilder builder = new WorkflowBuilder();
		String definingDocumentId = "";
		builder.id(definingDocumentId, workflow.getId());
		final State start = getState(workflow, workflow.getStart().getStateName());
		buildState(builder, workflow, start, true);
		State state = start;
		while(state.getEnd() == null || !state.getEnd().isTerminate()) {
			final Transition transition = state.getTransition();
			final State nextState = getState(workflow, transition.getNextState());
			buildTransition(builder, workflow, state, transition, nextState);
			buildState(builder, workflow, nextState, false);
			state = nextState;
		}
		buildEnd(builder, workflow, state, state.getEnd());
		return builder.getFlow();
	}
	
	private State getState(final Workflow workflow, final String name) {
		return workflow.getStates().stream().filter(state -> state.getName().equals(name)).findFirst().get();
	}
	
	private FlowBuilder buildState(FlowBuilder builder, final Workflow workflow, final State state, final boolean start) {
		if(start) {
			builder.viewNode(state.getName(), String.format(STATE_VDL_DOCUMENT_ID_FORMAT, state.getType())).markAsStartNode();
		}
		else {

			builder.viewNode(state.getName(), String.format(STATE_VDL_DOCUMENT_ID_FORMAT, state.getType()));
		}
		switch(state.getType()) {
			case CALLBACK:
				final CallbackState callback = (CallbackState) state;
				buildCallbackState(builder, workflow, callback);
				break;
			case EVENT:
				final EventState event = (EventState) state;
				buildEventState(builder, workflow, event);
				break;
			case FOREACH:
				final ForEachState forEach = (ForEachState) state;
				buildForEachState(builder, workflow, forEach);
				break;
			case INJECT:
				final InjectState inject = (InjectState) state;
				buildInjectState(builder, workflow, inject);
				break;
			case OPERATION:
				final OperationState operation = (OperationState) state;
				buildOperationState(builder, workflow, operation);
				break;
			case PARALLEL:
				final ParallelState parallel = (ParallelState) state;
				buildParallelState(builder, workflow, parallel);
				break;
			case SLEEP:
				final SleepState sleep = (SleepState) state;
				buildSleepState(builder, workflow, sleep);
				break;
			case SUBFLOW:
				break;
			case SWITCH:
				final SwitchState _switch = (SwitchState) state;
				buildSwitchState(builder, workflow, _switch);
				break;
			default:
				break;
		}
		return builder;
	}
	
	private FlowBuilder buildCallbackState(FlowBuilder builder, final Workflow workflow, final CallbackState callback) {
		builder.navigationCase().fromAction(callback.getAction().getName()).toViewId(callback.getId());
		return builder;
	}
	
	private FlowBuilder buildEventState(FlowBuilder builder, final Workflow workflow, final EventState event) {
		return builder;
	}
	
	private FlowBuilder buildForEachState(FlowBuilder builder, final Workflow workflow, final ForEachState forEach) {
		return builder;
	}
	
	private FlowBuilder buildInjectState(FlowBuilder builder, final Workflow workflow, final InjectState inject) {
		return builder;
	}
	
	private FlowBuilder buildOperationState(FlowBuilder builder, final Workflow workflow, final OperationState operation) {
		return builder;
	}
	
	private FlowBuilder buildParallelState(FlowBuilder builder, final Workflow workflow, final ParallelState parallel) {
		return builder;
	}
	
	private FlowBuilder buildSleepState(FlowBuilder builder, final Workflow workflow, final SleepState sleep) {
		return builder;
	}
	
	private FlowBuilder buildSwitchState(FlowBuilder builder, final Workflow workflow, final SwitchState _switch) {
		if(_switch.getDataConditions() != null) {
			final SwitchBuilder switchBuilder = builder.switchNode(_switch.getId());
			for(DataCondition dataCondition : _switch.getDataConditions()) {
				switchBuilder.switchCase().condition(dataCondition.getCondition());
			}
		}
		return builder;
	}
	
	private FlowBuilder buildTransition(FlowBuilder builder, final Workflow workflow, final State state, final Transition transition, final State nextState) {
		return builder;
	}
	
	private FlowBuilder buildEnd(FlowBuilder builder, final Workflow workflow, final State state, final End end) {
		builder.returnNode(state.getId());
		return builder;
	}
}
