package epf.workflow.schema;

/**
 * @author PC
 *
 */
public class ParallelStateBranch {

	/**
	 * 
	 */
	private String name;
	/**
	 * 
	 */
	private ActionDefinition[] actions;
	/**
	 * 
	 */
	private WorkflowTimeoutDefinition timeouts;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ActionDefinition[] getActions() {
		return actions;
	}
	public void setActions(ActionDefinition[] actions) {
		this.actions = actions;
	}
	public WorkflowTimeoutDefinition getTimeouts() {
		return timeouts;
	}
	public void setTimeouts(WorkflowTimeoutDefinition timeouts) {
		this.timeouts = timeouts;
	}
}
