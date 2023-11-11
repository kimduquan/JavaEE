package epf.workflow.schema.adapter;

import java.util.Arrays;
import epf.workflow.schema.WorkflowExecTimeoutDefinition;
import epf.workflow.schema.util.EitherJsonAdapter;

/**
 * @author PC
 *
 */
public class WorkflowExecTimeoutDefinitionAdapter extends EitherJsonAdapter<String, WorkflowExecTimeoutDefinition> {

	/**
	 * 
	 */
	public WorkflowExecTimeoutDefinitionAdapter() {
		super(String.class, WorkflowExecTimeoutDefinition.class, Arrays.asList());
	}
}
