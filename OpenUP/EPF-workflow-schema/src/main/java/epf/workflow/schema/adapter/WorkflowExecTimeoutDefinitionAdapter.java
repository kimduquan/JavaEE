package epf.workflow.schema.adapter;

import epf.workflow.schema.WorkflowExecTimeoutDefinition;
import epf.workflow.schema.util.StringOrObjectJsonAdapter;

/**
 * @author PC
 *
 */
public class WorkflowExecTimeoutDefinitionAdapter extends StringOrObjectJsonAdapter<WorkflowExecTimeoutDefinition> {

	/**
	 * 
	 */
	public WorkflowExecTimeoutDefinitionAdapter() {
		super(WorkflowExecTimeoutDefinition.class);
	}
}
