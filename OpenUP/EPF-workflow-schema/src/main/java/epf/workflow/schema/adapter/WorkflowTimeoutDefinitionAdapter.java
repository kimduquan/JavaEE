package epf.workflow.schema.adapter;

import epf.workflow.schema.WorkflowTimeoutDefinition;
import epf.workflow.schema.util.StringOrObjectJsonAdapter;

/**
 * @author PC
 *
 */
public class WorkflowTimeoutDefinitionAdapter extends StringOrObjectJsonAdapter<WorkflowTimeoutDefinition> {

	/**
	 * 
	 */
	public WorkflowTimeoutDefinitionAdapter() {
		super(WorkflowTimeoutDefinition.class);
	}
}
