package epf.workflow.schema;

/**
 * @author PC
 *
 */
public class EventState extends State {

	/**
	 * 
	 */
	private boolean exclusive = true;
	
	/**
	 * 
	 */
	private OnEventsDefinition[] onEvents;
	
	/**
	 * 
	 */
	private Object timeouts;
	
	/**
	 * 
	 */
	private Object stateDataFilter;
	
	/**
	 * 
	 */
	private Object transition;
	
	/**
	 * 
	 */
	private Object[] onErrors;
	
	/**
	 * 
	 */
	private Object end;
	
	/**
	 * 
	 */
	private String compensatedBy;
	
	/**
	 * 
	 */
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

	public Object getTimeouts() {
		return timeouts;
	}

	public void setTimeouts(Object timeouts) {
		this.timeouts = timeouts;
	}

	public Object getStateDataFilter() {
		return stateDataFilter;
	}

	public void setStateDataFilter(Object stateDataFilter) {
		this.stateDataFilter = stateDataFilter;
	}

	public Object getTransition() {
		return transition;
	}

	public void setTransition(Object transition) {
		this.transition = transition;
	}

	public Object[] getOnErrors() {
		return onErrors;
	}

	public void setOnErrors(Object[] onErrors) {
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
