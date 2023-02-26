package epf.workflow.state;

import java.util.concurrent.Callable;

import epf.workflow.WorkflowData;
import epf.workflow.WorkflowInstance;
import epf.workflow.action.Action;

/**
 * @author PC
 *
 */
public class Branch implements Callable<Void> {
	
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
	public Branch(Action[] actions, WorkflowData workflowData, WorkflowInstance workflowInstance) {
		this.actions = actions;
		this.workflowData = workflowData;
		this.workflowInstance = workflowInstance;
	}

	public Action[] getActions() {
		return actions;
	}

	public WorkflowData getWorkflowData() {
		return workflowData;
	}

	public WorkflowInstance getWorkflowInstance() {
		return workflowInstance;
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
}
