package epf.workflow.schema.event;

import jakarta.validation.constraints.NotNull;
import jakarta.nosql.Column;
import java.io.Serializable;
import java.util.List;
import org.eclipse.jnosql.mapping.Embeddable;
import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
import epf.workflow.schema.action.ActionDefinition;
import epf.workflow.schema.action.Mode;

@Embeddable
public class OnEventsDefinition implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@NotNull
	@Column
	@Description("References one or more unique event names in the defined workflow events")
	private List<String> eventRefs;
	
	@Column
	@Description("Specifies how actions are to be performed (in sequence or in parallel).")
	@DefaultValue("sequential")
	private Mode actionMode = Mode.sequential;
	
	@Column
	@Description("Actions to be performed")
	private List<ActionDefinition> actions;
	
	@Column
	@Description("Event data filter definition")
	private EventDataFilter eventDataFilter;

	public List<String> getEventRefs() {
		return eventRefs;
	}

	public void setEventRefs(List<String> eventRefs) {
		this.eventRefs = eventRefs;
	}

	public Mode getActionMode() {
		return actionMode;
	}

	public void setActionMode(Mode actionMode) {
		this.actionMode = actionMode;
	}

	public List<ActionDefinition> getActions() {
		return actions;
	}

	public void setActions(List<ActionDefinition> actions) {
		this.actions = actions;
	}

	public EventDataFilter getEventDataFilter() {
		return eventDataFilter;
	}

	public void setEventDataFilter(EventDataFilter eventDataFilter) {
		this.eventDataFilter = eventDataFilter;
	}
}
