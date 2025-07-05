package epf.workflow.schema.adapter;

import epf.workflow.schema.WorkflowTimeoutDefinition;
import epf.workflow.schema.util.StringOrObjectJsonAdapter;

public class StringOrWorkflowTimeoutDefinitionAdapter extends StringOrObjectJsonAdapter<WorkflowTimeoutDefinition> {

	public StringOrWorkflowTimeoutDefinitionAdapter() {
		super(WorkflowTimeoutDefinition.class);
	}
}
