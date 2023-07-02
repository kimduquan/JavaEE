package epf.workflow.schedule;

import java.net.URI;
import java.util.concurrent.Callable;
import epf.util.json.JsonUtil;
import epf.workflow.WorkflowData;
import epf.workflow.WorkflowRuntime;
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
	 * 
	 */
	private final URI uri;
	
	/**
	 * @param workflowRuntime
	 * @param workflowDefinition
	 * @param uri
	 */
	public Schedule(final WorkflowRuntime workflowRuntime, final WorkflowDefinition workflowDefinition, final URI uri) {
		this.workflowRuntime = workflowRuntime;
		this.workflowDefinition = workflowDefinition;
		this.uri = uri;
	}

	@Override
	public Void call() throws Exception {
		final WorkflowData workflowData = new WorkflowData();
		workflowData.setInput(JsonUtil.empty());
		workflowData.setOutput(JsonUtil.empty());
		workflowRuntime.start(workflowDefinition, workflowData, uri);
		return null;
	}
}
