package epf.workflow.schema.state;

import javax.json.bind.annotation.JsonbTypeAdapter;
import javax.validation.constraints.NotNull;
import epf.workflow.schema.ErrorDefinition;
import epf.workflow.schema.WorkflowTimeoutDefinition;
import epf.workflow.schema.adapter.TransitionOrEndAdapter;
import jakarta.nosql.Column;
import org.eclipse.jnosql.mapping.Embeddable;

/**
 * @author PC
 *
 */
@Embeddable
public class SwitchState extends State {

	/**
	 * 
	 */
	@Column
	private SwitchStateDataConditions[] dataConditions;
	/**
	 * 
	 */
	@Column
	private SwitchStateEventConditions[] eventConditions;
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
	private WorkflowTimeoutDefinition timeouts;
	/**
	 * 
	 */
	@NotNull
	@Column
	@JsonbTypeAdapter(value = TransitionOrEndAdapter.class)
	private Object defaultCondition;
	/**
	 * 
	 */
	@Column
	private String compensatedBy;
	/**
	 * 
	 */
	@Column
	private Boolean usedForCompensation = false;
	/**
	 * 
	 */
	@Column
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
	public WorkflowTimeoutDefinition getTimeouts() {
		return timeouts;
	}
	public void setTimeouts(WorkflowTimeoutDefinition timeouts) {
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
	public Boolean isUsedForCompensation() {
		return usedForCompensation;
	}
	public void setUsedForCompensation(Boolean usedForCompensation) {
		this.usedForCompensation = usedForCompensation;
	}
	public Object getMetadata() {
		return metadata;
	}
	public void setMetadata(Object metadata) {
		this.metadata = metadata;
	}
}
