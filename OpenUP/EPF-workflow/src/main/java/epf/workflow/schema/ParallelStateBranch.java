package epf.workflow.schema;

import javax.validation.constraints.NotNull;
import jakarta.nosql.mapping.Column;
import jakarta.nosql.mapping.Embeddable;

/**
 * @author PC
 *
 */
@Embeddable
public class ParallelStateBranch {

	/**
	 * 
	 */
	@NotNull
	@Column
	private String name;
	/**
	 * 
	 */
	@NotNull
	@Column
	private ActionDefinition[] actions;
	/**
	 * 
	 */
	@Column
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
