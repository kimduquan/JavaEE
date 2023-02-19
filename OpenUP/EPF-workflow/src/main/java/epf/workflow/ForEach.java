package epf.workflow;

import java.util.concurrent.Callable;

/**
 * @author PC
 *
 */
public class ForEach implements Callable<Void> {
	
	/**
	 * 
	 */
	private final Action[] actions;
	
	/**
	 * 
	 */
	private final WorkflowData workflowData;
	
	/**
	 * 
	 */
	private final WorkflowInstance workflowInstance;
	
	/**
	 * @param actions
	 * @param workflowData
	 * @param workflowInstance
	 */
	public ForEach(Action[] actions, WorkflowData workflowData, WorkflowInstance workflowInstance) {
		this.actions = actions;
		this.workflowData = workflowData;
		this.workflowInstance = workflowInstance;
	}

	@Override
	public Void call() throws Exception {
		for(Action action : actions) {
			if(workflowInstance.isTerminate()) {
				break;
			}
			action.call();
		}
		return null;
	}

	public Action[] getActions() {
		return actions;
	}

	public WorkflowData getWorkflowData() {
		return workflowData;
	}
}
