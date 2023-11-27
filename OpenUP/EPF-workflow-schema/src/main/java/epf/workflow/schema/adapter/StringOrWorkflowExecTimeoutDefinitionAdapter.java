package epf.workflow.schema.adapter;

import epf.workflow.schema.WorkflowExecTimeoutDefinition;
import epf.workflow.schema.util.StringOrObjectJsonAdapter;

/**
 * @author PC
 *
 */
public class StringOrWorkflowExecTimeoutDefinitionAdapter extends StringOrObjectJsonAdapter<WorkflowExecTimeoutDefinition> {

	/**
	 * 
	 */
	public StringOrWorkflowExecTimeoutDefinitionAdapter() {
		super(WorkflowExecTimeoutDefinition.class);
	}
}
