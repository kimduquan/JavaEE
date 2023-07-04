package epf.workflow.action;

import java.net.URI;

import epf.workflow.WorkflowRuntime;
import epf.workflow.schema.SubFlowRefDefinition;
import epf.workflow.schema.WorkflowData;
import epf.workflow.schema.WorkflowDefinition;
import epf.workflow.schema.action.ActionDefinition;

/**
 * @author PC
 *
 */
public class SubflowAction extends Action {
	
	/**
	 * 
	 */
	private final WorkflowDefinition subWorkflowDefinition;
	
	/**
	 * 
	 */
	private final SubFlowRefDefinition subFlowRefDefinition;
	
	/**
	 * 
	 */
	private final WorkflowRuntime workflow;
	
	/**
	 * 
	 */
	private final URI uri;

	/**
	 * @param workflowDefinition
	 * @param actionDefinition
	 * @param workflow
	 * @param subFlowRefDefinition
	 * @param subWorkflowDefinition
	 * @param workflowData
	 */
	public SubflowAction(
			WorkflowDefinition workflowDefinition, 
			ActionDefinition actionDefinition,
			WorkflowRuntime workflow,
			SubFlowRefDefinition subFlowRefDefinition,
			WorkflowDefinition subWorkflowDefinition,
			WorkflowData workflowData, 
			URI uri) {
		super(workflowDefinition, actionDefinition, workflowData);
		this.subWorkflowDefinition = subWorkflowDefinition;
		this.workflow = workflow;
		this.subFlowRefDefinition = subFlowRefDefinition;
		this.uri = uri;
	}

	public WorkflowDefinition getSubWorkflowDefinition() {
		return subWorkflowDefinition;
	}

	@Override
	protected void perform() throws Exception {
		workflow.start(subWorkflowDefinition, getWorkflowData(), uri);
	}

	public SubFlowRefDefinition getSubFlowRefDefinition() {
		return subFlowRefDefinition;
	}

}
