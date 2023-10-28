package epf.workflow.schema.event;

import jakarta.validation.constraints.NotNull;
import jakarta.nosql.Column;
import java.util.List;
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
	private List<String> eventRefs;
	
	/**
	 * 
	 */
	@Column
	private Mode actionMode = Mode.sequential;
	
	/**
	 * 
	 */
	@Column
	private List<ActionDefinition> actions;
	
	/**
	 * 
	 */
	@Column
	private EventDataFilters eventDataFilter;

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

	public EventDataFilters getEventDataFilter() {
		return eventDataFilter;
	}

	public void setEventDataFilter(EventDataFilters eventDataFilter) {
		this.eventDataFilter = eventDataFilter;
	}
}
