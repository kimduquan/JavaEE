package epf.workflow.schema;

import javax.validation.constraints.NotNull;

/**
 * @author PC
 *
 */
public class SwitchState extends State {

	/**
	 * 
	 */
	private SwitchStateDataConditions[] dataConditions;
	/**
	 * 
	 */
	private SwitchStateEventConditions[] eventConditions;
	/**
	 * 
	 */
	private StateDataFilters stateDataFilter;
	/**
	 * 
	 */
	private ErrorDefinition[] onErrors;
	/**
	 * 
	 */
	private Object timeouts;
	/**
	 * 
	 */
	@NotNull
	private Object defaultCondition;
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
	
	public SwitchStateDataConditions[] getDataConditions() {
		return dataConditions;
	}
	public void setDataConditions(SwitchStateDataConditions[] dataConditions) {
		this.dataConditions = dataConditions;
	}
	public SwitchStateEventConditions[] getEventConditions() {
		return eventConditions;
	}
	public void setEventConditions(SwitchStateEventConditions[] eventConditions) {
		this.eventConditions = eventConditions;
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
	public Object getTimeouts() {
		return timeouts;
	}
	public void setTimeouts(Object timeouts) {
		this.timeouts = timeouts;
	}
	public Object getDefaultCondition() {
		return defaultCondition;
	}
	public void setDefaultCondition(Object defaultCondition) {
		this.defaultCondition = defaultCondition;
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
