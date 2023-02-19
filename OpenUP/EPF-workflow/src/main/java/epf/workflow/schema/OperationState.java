package epf.workflow.schema;

import javax.validation.constraints.NotNull;
import jakarta.nosql.mapping.Column;
import jakarta.nosql.mapping.Embeddable;

/**
 * @author PC
 *
 */
@Embeddable
public class OperationState extends State {

	/**
	 * 
	 */
	@Column
	private Mode actionMode = Mode.sequential;
	
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
	private Object timeouts;
	
	/**
	 * 
	 */
	@Column
	private StateDataFilters stateDataFilter;
	
	/**
	 * 
	 */
	@Column
	private ErrorDefinition[] onErrors;
	
	/**
	 * 
	 */
	@Column
	private Object transition;
	
	/**
	 * 
	 */
	@Column
	private String compensatedBy;
	
	/**
	 * 
	 */
	@Column
	private boolean usedForCompensation = false;
	
	/**
	 * 
	 */
	@Column
	private Object metadata;
	
	/**
	 * 
	 */
	@Column
	private Object end;

	public Mode getActionMode() {
		return actionMode;
	}

	public void setActionMode(Mode actionMode) {
		this.actionMode = actionMode;
	}

	public ActionDefinition[] getActions() {
		return actions;
	}

	public void setActions(ActionDefinition[] actions) {
		this.actions = actions;
	}

	public Object getTimeouts() {
		return timeouts;
	}

	public void setTimeouts(Object timeouts) {
		this.timeouts = timeouts;
	}

	public StateDataFilters getStateDataFilter() {
		return stateDataFilter;
	}

	public void setStateDataFilter(StateDataFilters stateDataFilter) {
		this.stateDataFilter = stateDataFilter;
	}

	public ErrorDefinition[] getOnErrors() {
		return onErrors;
	}

	public void setOnErrors(ErrorDefinition[] onErrors) {
		this.onErrors = onErrors;
	}

	public Object getTransition() {
		return transition;
	}

	public void setTransition(Object transition) {
		this.transition = transition;
	}

	public String getCompensatedBy() {
		return compensatedBy;
	}

	public void setCompensatedBy(String compensatedBy) {
		this.compensatedBy = compensatedBy;
	}

	public boolean isUsedForCompensation() {
		return usedForCompensation;
	}

	public void setUsedForCompensation(boolean usedForCompensation) {
		this.usedForCompensation = usedForCompensation;
	}

	public Object getMetadata() {
		return metadata;
	}

	public void setMetadata(Object metadata) {
		this.metadata = metadata;
	}

	public Object getEnd() {
		return end;
	}

	public void setEnd(Object end) {
		this.end = end;
	}
}
