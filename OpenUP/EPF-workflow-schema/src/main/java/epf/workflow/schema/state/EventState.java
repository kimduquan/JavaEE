package epf.workflow.schema.state;

import jakarta.json.bind.annotation.JsonbTypeAdapter;
import jakarta.validation.constraints.NotNull;
import epf.workflow.schema.event.OnEventsDefinition;
import epf.workflow.schema.mapping.EndDefinitionConverter;
import epf.workflow.schema.mapping.TransitionDefinitionConverter;
import epf.workflow.schema.ErrorDefinition;
import epf.workflow.schema.WorkflowTimeoutDefinition;
import epf.workflow.schema.adapter.EndDefinitionAdapter;
import epf.workflow.schema.adapter.TransitionDefinitionAdapter;
import jakarta.nosql.Column;
import java.util.List;
import org.eclipse.jnosql.mapping.Convert;
import org.eclipse.jnosql.mapping.DiscriminatorValue;
import org.eclipse.jnosql.mapping.Embeddable;

/**
 * @author PC
 *
 */
@Embeddable
@DiscriminatorValue(Type.EVENT)
public class EventState extends State {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@Column
	private Boolean exclusive = true;
	
	/**
	 * 
	 */
	@NotNull
	@Column
	private List<OnEventsDefinition> onEvents;
	
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
	@Convert(TransitionDefinitionConverter.class)
	private Object transition;
	
	/**
	 * 
	 */
	@Column
	private List<ErrorDefinition> onErrors;
	
	/**
	 * 
	 */
	@Column
	@JsonbTypeAdapter(value = EndDefinitionAdapter.class)
	@Convert(EndDefinitionConverter.class)
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

	public Boolean isExclusive() {
		return exclusive;
	}

	public void setExclusive(Boolean exclusive) {
		this.exclusive = exclusive;
	}

	public List<OnEventsDefinition> getOnEvents() {
		return onEvents;
	}

	public void setOnEvents(List<OnEventsDefinition> onEvents) {
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

	public List<ErrorDefinition> getOnErrors() {
		return onErrors;
	}

	public void setOnErrors(List<ErrorDefinition> onErrors) {
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
