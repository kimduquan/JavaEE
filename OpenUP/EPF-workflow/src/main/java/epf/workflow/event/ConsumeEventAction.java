package epf.workflow.event;

import epf.workflow.WorkflowData;
import epf.workflow.action.Action;
import epf.workflow.action.schema.ActionDefinition;
import epf.workflow.schema.WorkflowDefinition;

/**
 * @author PC
 *
 */
public class ConsumeEventAction extends Action {

	/**
	 * @param workflowDefinition
	 * @param actionDefinition
	 * @param workflowData
	 */
	public ConsumeEventAction(WorkflowDefinition workflowDefinition, ActionDefinition actionDefinition,
			WorkflowData workflowData) {
		super(workflowDefinition, actionDefinition, workflowData);
	}

	@Override
	protected void perform() throws Exception {
		
	}
}
