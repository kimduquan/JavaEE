package epf.workflow.schema;

/**
 * @author PC
 *
 */
public class CallbackState extends State {

	/**
	 * 
	 */
	private ActionDefinition action;
	/**
	 * 
	 */
	private String eventRef;
	/**
	 * 
	 */
	private Object timeouts;
	/**
	 * 
	 */
	private Object eventDataFilter;
	/**
	 * 
	 */
	private Object stateDataFilter;
	/**
	 * 
	 */
	private Object[] onErrors;
	/**
	 * 
	 */
	private Object transition;
	/**
	 * 
	 */
	private Object end;
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
	
	public ActionDefinition getAction() {
		return action;
	}
	public void setAction(ActionDefinition action) {
		this.action = action;
	}
	public String getEventRef() {
		return eventRef;
	}
	public void setEventRef(String eventRef) {
		this.eventRef = eventRef;
	}
	public Object getTimeouts() {
		return timeouts;
	}
	public void setTimeouts(Object timeouts) {
		this.timeouts = timeouts;
	}
	public Object getEventDataFilter() {
		return eventDataFilter;
	}
	public void setEventDataFilter(Object eventDataFilter) {
		this.eventDataFilter = eventDataFilter;
	}
	public Object getStateDataFilter() {
		return stateDataFilter;
	}
	public void setStateDataFilter(Object stateDataFilter) {
		this.stateDataFilter = stateDataFilter;
	}
	public Object[] getOnErrors() {
		return onErrors;
	}
	public void setOnErrors(Object[] onErrors) {
		this.onErrors = onErrors;
	}
	public Object getTransition() {
		return transition;
	}
	public void setTransition(Object transition) {
		this.transition = transition;
	}
	public Object getEnd() {
		return end;
	}
	public void setEnd(Object end) {
		this.end = end;
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
}
