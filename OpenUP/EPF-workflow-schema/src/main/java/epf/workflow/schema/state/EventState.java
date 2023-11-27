package epf.workflow.schema.state;

import jakarta.json.bind.annotation.JsonbTypeAdapter;
import jakarta.validation.constraints.NotNull;
import epf.workflow.schema.event.OnEventsDefinition;
import epf.workflow.schema.util.BooleanOrObject;
import epf.workflow.schema.util.StringOrObject;
import epf.workflow.schema.EndDefinition;
import epf.workflow.schema.ErrorDefinition;
import epf.workflow.schema.TransitionDefinition;
import epf.workflow.schema.WorkflowTimeoutDefinition;
import epf.workflow.schema.adapter.BooleanOrEndDefinitionAdapter;
import epf.workflow.schema.adapter.StringOrTransitionDefinitionAdapter;
import jakarta.nosql.Column;
import java.util.List;
import java.util.Map;

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
	@JsonbTypeAdapter(value = StringOrTransitionDefinitionAdapter.class)
	private StringOrObject<TransitionDefinition> transition;
	
	/**
	 * 
	 */
	@Column
	private List<ErrorDefinition> onErrors;
	
	/**
	 * 
	 */
	@Column
	@JsonbTypeAdapter(value = BooleanOrEndDefinitionAdapter.class)
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
	private Map<String, String> metadata;
	
	/**
	 * 
	 */
	public EventState() {
		setType_(Type.event);
	}

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

	public StringOrObject<TransitionDefinition> getTransition() {
		return transition;
	}

	public void setTransition(StringOrObject<TransitionDefinition> transition) {
		this.transition = transition;
	}

	public List<ErrorDefinition> getOnErrors() {
		return onErrors;
	}

	public void setOnErrors(List<ErrorDefinition> onErrors) {
		this.onErrors = onErrors;
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

	public Map<String, String> getMetadata() {
		return metadata;
	}

	public void setMetadata(Map<String, String> metadata) {
		this.metadata = metadata;
	}
}
