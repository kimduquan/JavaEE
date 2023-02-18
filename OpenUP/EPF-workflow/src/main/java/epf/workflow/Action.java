package epf.workflow;

import java.time.Duration;
import java.util.concurrent.Callable;
import epf.workflow.schema.ActionDefinition;
import epf.workflow.schema.WorkflowDefinition;

/**
 * @author PC
 *
 */
public abstract class Action implements Callable<Void> {
	
	/**
	 * 
	 */
	private final WorkflowDefinition workflowDefinition;
	
	/**
	 * 
	 */
	private final ActionDefinition actionDefinition;
	
	/**
	 * 
	 */
	private final WorkflowData workflowData;
	
	/**
	 * @param workflowDefinition
	 * @param actionDefinition
	 * @param workflowData
	 */
	public Action(WorkflowDefinition workflowDefinition, ActionDefinition actionDefinition, WorkflowData workflowData) {
		this.workflowDefinition = workflowDefinition;
		this.actionDefinition = actionDefinition;
		this.workflowData = workflowData;
	}
	
	protected void sleepBefore() throws Exception {
		if(getActionDefinition().getSleep() != null && getActionDefinition().getSleep().getBefore() != null) {
			final Duration before = Duration.parse(getActionDefinition().getSleep().getBefore());
			Thread.sleep(before.toMillis());
		}
	}
	
	protected void sleepAfter() throws Exception {
		if(getActionDefinition().getSleep() != null && getActionDefinition().getSleep().getAfter() != null) {
			final Duration after = Duration.parse(getActionDefinition().getSleep().getAfter());
			Thread.sleep(after.toMillis());
		}
	}

	public ActionDefinition getActionDefinition() {
		return actionDefinition;
	}

	public WorkflowData getWorkflowData() {
		return workflowData;
	}

	public WorkflowDefinition getWorkflowDefinition() {
		return workflowDefinition;
	}

	@Override
	public Void call() throws Exception {
		sleepBefore();
		if(getActionDefinition().getRetryRef() != null) {
			final Callable<Void> callable = new Callable<Void>() {
				@Override
				public Void call() throws Exception {
					perform();
					return null;
				}};
			final Retry<Void> retry = new Retry<>(callable, getWorkflowDefinition(), getActionDefinition());
			retry.call();
		}
		else {
			perform();
		}
		sleepAfter();
		return null;
	}
	
	protected abstract void perform() throws Exception;
}
