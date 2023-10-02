package epf.workflow.schema.event;

import jakarta.validation.constraints.NotNull;

import jakarta.nosql.Column;
import org.eclipse.jnosql.mapping.Embeddable;

import epf.workflow.schema.action.ActionDefinition;
import epf.workflow.schema.action.Mode;

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
