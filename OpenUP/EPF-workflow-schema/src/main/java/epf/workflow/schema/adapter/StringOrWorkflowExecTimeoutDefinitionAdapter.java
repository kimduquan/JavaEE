package epf.workflow.schema.adapter;

import epf.workflow.schema.WorkflowExecTimeoutDefinition;
import epf.workflow.schema.util.StringOrObjectJsonAdapter;

public class StringOrWorkflowExecTimeoutDefinitionAdapter extends StringOrObjectJsonAdapter<WorkflowExecTimeoutDefinition> {

	public StringOrWorkflowExecTimeoutDefinitionAdapter() {
		super(WorkflowExecTimeoutDefinition.class);
	}
}
