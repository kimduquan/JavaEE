package epf.workflow.schema.adapter;

import epf.workflow.schema.WorkflowExecTimeoutDefinition;

/**
 * @author PC
 *
 */
public class WorkflowExecTimeoutDefinitionAdapter extends StringOrObjectAdapter<WorkflowExecTimeoutDefinition> {

	/**
	 * 
	 */
	public WorkflowExecTimeoutDefinitionAdapter() {
		super(WorkflowExecTimeoutDefinition.class);
	}
}
