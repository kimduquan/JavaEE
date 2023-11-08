package epf.workflow.schema.adapter;

import epf.workflow.schema.WorkflowExecTimeoutDefinition;
import epf.workflow.schema.util.EitherAdapter;

/**
 * @author PC
 *
 */
public class WorkflowExecTimeoutDefinitionAdapter extends EitherAdapter<String, WorkflowExecTimeoutDefinition> {

	/**
	 * 
	 */
	public WorkflowExecTimeoutDefinitionAdapter() {
		super(String.class, WorkflowExecTimeoutDefinition.class);
	}
}
