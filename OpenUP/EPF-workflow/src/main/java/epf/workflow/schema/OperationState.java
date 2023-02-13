package epf.workflow.schema;

/**
 * @author PC
 *
 */
public class OperationState extends State {

	/**
	 * 
	 */
	private ActionMode actionMode = ActionMode.sequential;
	
	/**
	 * 
	 */
	private ActionDefinition[] actions;
	
	/**
	 * 
	 */
	private Object timeouts;
	
	/**
	 * 
	 */
	private Object stateDataFilter;
	
	/**
	 * 
	 */
	private ErrorDefinition[] onErrors;
	
	/**
	 * 
	 */
	private Object transition;
	
	/**
	 * 
	 */
	private String compensatedBy;
	
	/**
	 * 
	 */
	private boolean usedForCompensation = false;
	
	/**
	 * 
	 */
	private Object metadata;
	
	/**
	 * 
	 */
	private Object end;

	public ActionMode getActionMode() {
		return actionMode;
	}

	public void setActionMode(ActionMode actionMode) {
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

	public Object getStateDataFilter() {
		return stateDataFilter;
	}

	public void setStateDataFilter(Object stateDataFilter) {
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
