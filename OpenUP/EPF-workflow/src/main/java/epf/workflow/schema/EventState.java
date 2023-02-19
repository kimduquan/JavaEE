package epf.workflow.schema;

import javax.json.bind.annotation.JsonbTypeAdapter;
import javax.validation.constraints.NotNull;
import epf.workflow.schema.adapter.EndDefinitionAdapter;
import epf.workflow.schema.adapter.TransitionDefinitionAdapter;
import jakarta.nosql.mapping.Column;
import jakarta.nosql.mapping.Embeddable;

/**
 * @author PC
 *
 */
@Embeddable
public class EventState extends State {

	/**
	 * 
	 */
	@Column
	private boolean exclusive = true;
	
	/**
	 * 
	 */
	@NotNull
	@Column
	private OnEventsDefinition[] onEvents;
	
	/**
	 * 
	 */
	@Column
	private WorkflowTimeoutDefinition timeouts;
	
	/**
	 * 
	 */
	@Column
	private StateDataFilters stateDataFilter;
	
	/**
	 * 
	 */
	@Column
	@JsonbTypeAdapter(value = TransitionDefinitionAdapter.class)
	private Object transition;
	
	/**
	 * 
	 */
	@Column
	private ErrorDefinition[] onErrors;
	
	/**
	 * 
	 */
	@Column
	@JsonbTypeAdapter(value = EndDefinitionAdapter.class)
	private Object end;
	
	/**
	 * 
	 */
	@Column
	private String compensatedBy;
	
	/**
	 * 
	 */
	@Column
	private Object metadata;

	public boolean isExclusive() {
		return exclusive;
	}

	public void setExclusive(boolean exclusive) {
		this.exclusive = exclusive;
	}

	public OnEventsDefinition[] getOnEvents() {
		return onEvents;
	}

	public void setOnEvents(OnEventsDefinition[] onEvents) {
		this.onEvents = onEvents;
	}

	public WorkflowTimeoutDefinition getTimeouts() {
		return timeouts;
	}

	public void setTimeouts(WorkflowTimeoutDefinition timeouts) {
		this.timeouts = timeouts;
	}

	public StateDataFilters getStateDataFilter() {
		return stateDataFilter;
	}

	public void setStateDataFilter(StateDataFilters stateDataFilter) {
		this.stateDataFilter = stateDataFilter;
	}

	public Object getTransition() {
		return transition;
	}

	public void setTransition(Object transition) {
		this.transition = transition;
	}

	public ErrorDefinition[] getOnErrors() {
		return onErrors;
	}

	public void setOnErrors(ErrorDefinition[] onErrors) {
		this.onErrors = onErrors;
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

	public Object getMetadata() {
		return metadata;
	}

	public void setMetadata(Object metadata) {
		this.metadata = metadata;
	}
}
