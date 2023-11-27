package epf.workflow.schema.adapter;

import epf.workflow.schema.WorkflowError;
import epf.workflow.schema.util.StringOrArrayJsonAdapter;

/**
 * 
 */
public class StringOrArrayWorkflowErrorAdapter extends StringOrArrayJsonAdapter<WorkflowError> {

	/**
	 * 
	 */
	public StringOrArrayWorkflowErrorAdapter() {
		super(WorkflowError.class);
	}

}
