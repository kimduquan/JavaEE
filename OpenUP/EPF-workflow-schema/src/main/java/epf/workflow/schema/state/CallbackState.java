package epf.workflow.schema.state;

import jakarta.json.bind.annotation.JsonbTypeAdapter;
import jakarta.validation.constraints.NotNull;
import epf.workflow.schema.event.EventDataFilters;
import epf.workflow.schema.util.BooleanOrObject;
import epf.workflow.schema.util.StringOrObject;
import epf.workflow.schema.EndDefinition;
import epf.workflow.schema.ErrorDefinition;
import epf.workflow.schema.TransitionDefinition;
import epf.workflow.schema.WorkflowTimeoutDefinition;
import epf.workflow.schema.action.ActionDefinition;
import epf.workflow.schema.adapter.EndDefinitionAdapter;
import epf.workflow.schema.adapter.TransitionDefinitionAdapter;
import jakarta.nosql.Column;
import java.util.List;
import org.eclipse.jnosql.mapping.DiscriminatorValue;
import org.eclipse.jnosql.mapping.Embeddable;

/**
 * @author PC
 *
 */
@Embeddable
@DiscriminatorValue(Type.CALLBACK)
public class CallbackState extends State {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	@NotNull
	@Column
	private ActionDefinition action;
	/**
	 * 
	 */
	@NotNull
	@Column
	private String eventRef;
	/**
	 * 
	 */
	@Column
	private WorkflowTimeoutDefinition timeouts;
	/**
	 * 
	 */
	@Column
	private EventDataFilters eventDataFilter;
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
	@JsonbTypeAdapter(value = TransitionDefinitionAdapter.class)
	private StringOrObject<TransitionDefinition> transition;
	/**
	 * 
	 */
	@Column
	@JsonbTypeAdapter(value = EndDefinitionAdapter.class)
	private BooleanOrObject<EndDefinition> end;
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
	
	/**
	 * 
	 */
	public CallbackState() {
		setType(Type.callback);
	}
	
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
	public WorkflowTimeoutDefinition getTimeouts() {
		return timeouts;
	}
	public void setTimeouts(WorkflowTimeoutDefinition timeouts) {
		this.timeouts = timeouts;
	}
	public EventDataFilters getEventDataFilter() {
		return eventDataFilter;
	}
	public void setEventDataFilter(EventDataFilters eventDataFilter) {
		this.eventDataFilter = eventDataFilter;
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
	public StringOrObject<TransitionDefinition> getTransition() {
		return transition;
	}
	public void setTransition(StringOrObject<TransitionDefinition> transition) {
		this.transition = transition;
	}
	public BooleanOrObject<EndDefinition> getEnd() {
		return end;
	}
	public void setEnd(BooleanOrObject<EndDefinition> end) {
		this.end = end;
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
