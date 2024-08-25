package epf.workflow.error;

import java.net.URI;

import epf.workflow.model.WorkflowData;
import epf.workflow.schema.ErrorDefinition;
import epf.workflow.schema.WorkflowDefinition;
import epf.workflow.schema.WorkflowError;
import epf.workflow.schema.action.ActionDefinition;
import epf.workflow.schema.state.State;

/**
 * 
 */
public class WorkflowErrorException extends WorkflowException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	private final WorkflowDefinition workflowDefinition;
	
	/**
	 * 
	 */
	private final State state;
	
	/**
	 * 
	 */
	private final ActionDefinition actionDefinition;
	
	/**
	 * 
	 */
	private final ErrorDefinition errorDefinition;
	
	/**
	 * 
	 */
	private final URI instance;
	
	/**
	 * 
	 */
	private final WorkflowData workflowData;

	/**
	 * @param workflowError
	 * @param workflowDefinition
	 * @param workflowData
	 * @param state
	 * @param instance
	 * @param errorDefinition
	 * @param actionDefinition
	 */
	public WorkflowErrorException(final WorkflowError workflowError, final WorkflowDefinition workflowDefinition, final State state, final ActionDefinition actionDefinition, final ErrorDefinition errorDefinition, final URI instance, final WorkflowData workflowData) {
		super(workflowError);
		this.workflowDefinition = workflowDefinition;
		this.state = state;
		this.actionDefinition = actionDefinition;
		this.errorDefinition = errorDefinition;
		this.instance = instance;
		this.workflowData = workflowData;
	}

	public WorkflowDefinition getWorkflowDefinition() {
		return workflowDefinition;
	}

	public State getState() {
		return state;
	}

	public ActionDefinition getActionDefinition() {
		return actionDefinition;
	}

	public ErrorDefinition getErrorDefinition() {
		return errorDefinition;
	}

	public URI getInstance() {
		return instance;
	}

	public WorkflowData getWorkflowData() {
		return workflowData;
	}
}
