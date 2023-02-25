package epf.workflow;

import java.util.concurrent.Callable;
import epf.util.json.JsonUtil;
import epf.workflow.schema.WorkflowDefinition;

/**
 * @author PC
 *
 */
public class Schedule implements Callable<Void> {
	
	/**
	 * 
	 */
	private final WorkflowRuntime workflowRuntime;

	/**
	 * 
	 */
	private final WorkflowDefinition workflowDefinition;
	
	/**
	 * @param workflowRuntime
	 * @param workflowDefinition
	 */
	public Schedule(final WorkflowRuntime workflowRuntime, final WorkflowDefinition workflowDefinition) {
		this.workflowRuntime = workflowRuntime;
		this.workflowDefinition = workflowDefinition;
	}

	@Override
	public Void call() throws Exception {
		final WorkflowData workflowData = new WorkflowData();
		workflowData.setInput(JsonUtil.empty());
		workflowData.setOutput(JsonUtil.empty());
		workflowRuntime.start(workflowDefinition, workflowData);
		return null;
	}
}
