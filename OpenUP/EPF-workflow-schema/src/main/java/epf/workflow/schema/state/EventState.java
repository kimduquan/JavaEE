package epf.workflow.schema.state;

import java.util.List;
import java.util.Map;
import org.eclipse.jnosql.mapping.DiscriminatorValue;
import org.eclipse.jnosql.mapping.Embeddable;
import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
import epf.nosql.schema.BooleanOrObject;
import epf.nosql.schema.StringOrObject;
import epf.workflow.schema.EndDefinition;
import epf.workflow.schema.TransitionDefinition;
import epf.workflow.schema.WorkflowTimeoutDefinition;
import epf.workflow.schema.adapter.BooleanOrEndDefinitionAdapter;
import epf.workflow.schema.adapter.StringOrTransitionDefinitionAdapter;
import epf.workflow.schema.error.ErrorDefinition;
import epf.workflow.schema.event.OnEventsDefinition;
import jakarta.json.bind.annotation.JsonbTypeAdapter;
import jakarta.nosql.Column;
import jakarta.validation.constraints.NotNull;

@Embeddable
@DiscriminatorValue(Type.EVENT)
public class EventState extends State {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@NotNull
	@Column
	@Description("Unique State name")
	private String name;
	
	@NotNull
	@Column
	@Description("State type")
	private String type;
	
	@Column
	@Description("If true, consuming one of the defined events causes its associated actions to be performed. If false, all of the defined events must be consumed in order for actions to be performed.")
	@DefaultValue("true")
	private Boolean exclusive = true;
	
	@NotNull
	@Column
	@Description("Define the events to be consumed and optional actions to be performed")
	private List<OnEventsDefinition> onEvents;
	
	@Column
	@Description("State specific timeout settings")
	private WorkflowTimeoutDefinition timeouts;
	
	@Column
	@Description("State data filter")
	private StateDataFilter stateDataFilter;
	
	@Column
	@Description("Next transition of the workflow after all the actions have been performed")
	@JsonbTypeAdapter(value = StringOrTransitionDefinitionAdapter.class)
	private StringOrObject<TransitionDefinition> transition;
	
	@Column
	@Description("States error handling definitions")
	private List<ErrorDefinition> onErrors;
	
	@Column
	@Description("Is this state an end state")
	@JsonbTypeAdapter(value = BooleanOrEndDefinitionAdapter.class)
	private BooleanOrObject<EndDefinition> end;
	
	@Column
	@Description("Unique name of a workflow state which is responsible for compensation of this state")
	private String compensatedBy;
	
	@Column
	@Description("Metadata information")
	private Map<String, String> metadata;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Boolean getExclusive() {
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

	public StateDataFilter getStateDataFilter() {
		return stateDataFilter;
	}

	public void setStateDataFilter(StateDataFilter stateDataFilter) {
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
