package epf.workflow.schema;

/**
 * @author PC
 *
 */
public class OnEventsDefinition {

	/**
	 * 
	 */
	private String[] eventRefs;
	
	/**
	 * 
	 */
	private ActionMode actionMode = ActionMode.sequential;
	
	/**
	 * 
	 */
	private ActionDefinition[] actions;
	
	/**
	 * 
	 */
	private EventDataFilters eventDataFilter;

	public String[] getEventRefs() {
		return eventRefs;
	}

	public void setEventRefs(String[] eventRefs) {
		this.eventRefs = eventRefs;
	}

	public ActionMode getActionMode() {
		return actionMode;
	}

	public void setActionMode(ActionMode actionMode) {
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
