package epf.workflow.event.schema;

import javax.validation.constraints.NotNull;
import epf.workflow.action.schema.ActionDefinition;
import epf.workflow.action.schema.Mode;
import jakarta.nosql.mapping.Column;
import jakarta.nosql.mapping.Embeddable;

/**
 * @author PC
 *
 */
@Embeddable
public class OnEventsDefinition {

	/**
	 * 
	 */
	@NotNull
	@Column
	private String[] eventRefs;
	
	/**
	 * 
	 */
	@Column
	private Mode actionMode = Mode.sequential;
	
	/**
	 * 
	 */
	@Column
	private ActionDefinition[] actions;
	
	/**
	 * 
	 */
	@Column
	private EventDataFilters eventDataFilter;

	public String[] getEventRefs() {
		return eventRefs;
	}

	public void setEventRefs(String[] eventRefs) {
		this.eventRefs = eventRefs;
	}

	public Mode getActionMode() {
		return actionMode;
	}

	public void setActionMode(Mode actionMode) {
		this.actionMode = actionMode;
	}

	public ActionDefinition[] getActions() {
		return actions;
	}

	public void setActions(ActionDefinition[] actions) {
		this.actions = actions;
	}

	public EventDataFilters getEventDataFilter() {
		return eventDataFilter;
	}

	public void setEventDataFilter(EventDataFilters eventDataFilter) {
		this.eventDataFilter = eventDataFilter;
	}
}
