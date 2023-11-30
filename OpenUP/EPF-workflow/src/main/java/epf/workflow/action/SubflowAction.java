package epf.workflow.action;

import java.net.URI;
import epf.workflow.schema.SubFlowRefDefinition;
import epf.workflow.WorkflowData;
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
	private final URI uri;

	/**
	 * @param workflowDefinition
	 * @param actionDefinition
	 * @param subFlowRefDefinition
	 * @param subWorkflowDefinition
	 * @param workflowData
	 * @param uri
	 */
	public SubflowAction(
			WorkflowDefinition workflowDefinition, 
			ActionDefinition actionDefinition,
			SubFlowRefDefinition subFlowRefDefinition,
			WorkflowDefinition subWorkflowDefinition,
			WorkflowData workflowData, 
			URI uri) {
		super(workflowDefinition, actionDefinition, workflowData);
		this.subWorkflowDefinition = subWorkflowDefinition;
		this.subFlowRefDefinition = subFlowRefDefinition;
		this.uri = uri;
	}

	public WorkflowDefinition getSubWorkflowDefinition() {
		return subWorkflowDefinition;
	}

	@Override
	protected void perform() throws Exception {
	}

	public SubFlowRefDefinition getSubFlowRefDefinition() {
		return subFlowRefDefinition;
	}

	public URI getUri() {
		return uri;
	}

}
