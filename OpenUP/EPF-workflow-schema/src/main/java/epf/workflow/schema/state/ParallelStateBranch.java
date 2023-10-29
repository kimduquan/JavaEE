package epf.workflow.schema.state;

import jakarta.validation.constraints.NotNull;
import epf.workflow.schema.WorkflowTimeoutDefinition;
import epf.workflow.schema.action.ActionDefinition;
import jakarta.nosql.Column;
import java.io.Serializable;
import java.util.List;
import org.eclipse.jnosql.mapping.Embeddable;

/**
 * @author PC
 *
 */
@Embeddable
public class ParallelStateBranch implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
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
	private List<ActionDefinition> actions;
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
	public List<ActionDefinition> getActions() {
		return actions;
	}
	public void setActions(List<ActionDefinition> actions) {
		this.actions = actions;
	}
	public WorkflowTimeoutDefinition getTimeouts() {
		return timeouts;
	}
	public void setTimeouts(WorkflowTimeoutDefinition timeouts) {
		this.timeouts = timeouts;
	}
}
