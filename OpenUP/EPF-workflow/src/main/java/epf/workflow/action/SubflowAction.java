package epf.workflow.action;

import epf.workflow.WorkflowData;
import epf.workflow.WorkflowRuntime;
import epf.workflow.action.schema.ActionDefinition;
import epf.workflow.schema.SubFlowRefDefinition;
import epf.workflow.schema.WorkflowDefinition;

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
			WorkflowData workflowData) {
		super(workflowDefinition, actionDefinition, workflowData);
		this.subWorkflowDefinition = subWorkflowDefinition;
		this.workflow = workflow;
		this.subFlowRefDefinition = subFlowRefDefinition;
	}

	public WorkflowDefinition getSubWorkflowDefinition() {
		return subWorkflowDefinition;
	}

	@Override
	protected void perform() throws Exception {
		workflow.start(subWorkflowDefinition, getWorkflowData());
	}

	public SubFlowRefDefinition getSubFlowRefDefinition() {
		return subFlowRefDefinition;
	}

}
