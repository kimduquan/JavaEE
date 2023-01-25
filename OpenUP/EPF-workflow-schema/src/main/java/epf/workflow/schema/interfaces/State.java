package epf.workflow.schema.interfaces;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import epf.workflow.schema.end.End;
import epf.workflow.schema.filters.StateDataFilter;
import epf.workflow.schema.states.DefaultState.Type;
import epf.workflow.schema.timeouts.TimeoutsDefinition;
import epf.workflow.schema.transitions.Transition;

/**
 * @author PC
 *
 */
public class State implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private String id;
	/**
	 * 
	 */
	private String name;
	/**
	 * 
	 */
	private Type type;
	/**
	 * 
	 */
	private End end;
	/**
	 * 
	 */
	private StateDataFilter stateDataFilter;
	/**
	 * 
	 */
	private Transition transition;
	/**
	 * 
	 */
	private List<Error> onErrors;
	/**
	 * 
	 */
	private String compensatedBy;
	/**
	 * 
	 */
	private Map<String, String> metadata;
	/**
	 * 
	 */
	private TimeoutsDefinition timeouts;
	
	public String getId() {
		return id;
	}
	public void setId(final String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(final String name) {
		this.name = name;
	}
	public Type getType() {
		return type;
	}
	public void setType(final Type type) {
		this.type = type;
	}
	public End getEnd() {
		return end;
	}
	public void setEnd(final End end) {
		this.end = end;
	}
	public StateDataFilter getStateDataFilter() {
		return stateDataFilter;
	}
	public void setStateDataFilter(final StateDataFilter stateDataFilter) {
		this.stateDataFilter = stateDataFilter;
	}
	public Transition getTransition() {
		return transition;
	}
	public void setTransition(final Transition transition) {
		this.transition = transition;
	}
	public List<Error> getOnErrors() {
		return onErrors;
	}
	public void setOnErrors(final List<Error> onErrors) {
		this.onErrors = onErrors;
	}
	public String getCompensatedBy() {
		return compensatedBy;
	}
	public void setCompensatedBy(final String compensatedBy) {
		this.compensatedBy = compensatedBy;
	}
	public Map<String, String> getMetadata() {
		return metadata;
	}
	public void setMetadata(final Map<String, String> metadata) {
		this.metadata = metadata;
	}
	public TimeoutsDefinition getTimeouts() {
		return timeouts;
	}
	public void setTimeouts(final TimeoutsDefinition timeouts) {
		this.timeouts = timeouts;
	}
}