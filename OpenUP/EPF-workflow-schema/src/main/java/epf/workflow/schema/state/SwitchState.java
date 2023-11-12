package epf.workflow.schema.state;

import jakarta.json.bind.annotation.JsonbTypeAdapter;
import jakarta.validation.constraints.NotNull;
import epf.workflow.schema.ErrorDefinition;
import epf.workflow.schema.TransitionOrEnd;
import epf.workflow.schema.WorkflowTimeoutDefinition;
import epf.workflow.schema.adapter.TransitionOrEndAdapter;
import jakarta.nosql.Column;
import java.util.List;
import org.eclipse.jnosql.mapping.DiscriminatorValue;
import org.eclipse.jnosql.mapping.Embeddable;

/**
 * @author PC
 *
 */
@Embeddable
@DiscriminatorValue(Type.SWITCH)
public class SwitchState extends State {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	@Column
	private List<SwitchStateDataConditions> dataConditions;
	/**
	 * 
	 */
	@Column
	private List<SwitchStateEventConditions> eventConditions;
	/**
	 * 
	 */
	@Column
	private StateDataFilters stateDataFilter;
	/**
	 * 
	 */
	@Column
	private List<ErrorDefinition> onErrors;
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
	private TransitionOrEnd defaultCondition;
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
	
	public List<SwitchStateDataConditions> getDataConditions() {
		return dataConditions;
	}
	public void setDataConditions(List<SwitchStateDataConditions> dataConditions) {
		this.dataConditions = dataConditions;
	}
	public List<SwitchStateEventConditions> getEventConditions() {
		return eventConditions;
	}
	public void setEventConditions(List<SwitchStateEventConditions> eventConditions) {
		this.eventConditions = eventConditions;
	}
	public StateDataFilters getStateDataFilter() {
		return stateDataFilter;
	}
	public void setStateDataFilter(StateDataFilters stateDataFilter) {
		this.stateDataFilter = stateDataFilter;
	}
	public List<ErrorDefinition> getOnErrors() {
		return onErrors;
	}
	public void setOnErrors(List<ErrorDefinition> onErrors) {
		this.onErrors = onErrors;
	}
	public WorkflowTimeoutDefinition getTimeouts() {
		return timeouts;
	}
	public void setTimeouts(WorkflowTimeoutDefinition timeouts) {
		this.timeouts = timeouts;
	}
	public TransitionOrEnd getDefaultCondition() {
		return defaultCondition;
	}
	public void setDefaultCondition(TransitionOrEnd defaultCondition) {
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
