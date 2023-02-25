package epf.workflow.schema.adapter;

import epf.workflow.adapter.StringOrObjectAdapter;
import epf.workflow.schema.WorkflowExecTimeoutDefinition;

/**
 * @author PC
 *
 */
public class WorkflowExecTimeoutDefinitionAdapter extends StringOrObjectAdapter {

	/**
	 * 
	 */
	public WorkflowExecTimeoutDefinitionAdapter() {
		super(WorkflowExecTimeoutDefinition.class);
	}
}
