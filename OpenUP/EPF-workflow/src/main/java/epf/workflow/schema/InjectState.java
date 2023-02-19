package epf.workflow.schema;

import javax.json.JsonValue;
import javax.validation.constraints.NotNull;

/**
 * @author PC
 *
 */
public class InjectState extends State {

	/**
	 * 
	 */
	@NotNull
	private JsonValue data;
	/**
	 * 
	 */
	private StateDataFilters stateDataFilter;
	/**
	 * 
	 */
	private Object transition;
	/**
	 * 
	 */
	private String compensatedBy;
	/**
	 * 
	 */
	private boolean usedForCompensation;
	/**
	 * 
	 */
	private Object metadata;
	/**
	 * 
	 */
	private Object end;
	
	public JsonValue getData() {
		return data;
	}
	public void setData(JsonValue data) {
		this.data = data;
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
	public String getCompensatedBy() {
		return compensatedBy;
	}
	public void setCompensatedBy(String compensatedBy) {
		this.compensatedBy = compensatedBy;
	}
	public boolean isUsedForCompensation() {
		return usedForCompensation;
	}
	public void setUsedForCompensation(boolean usedForCompensation) {
		this.usedForCompensation = usedForCompensation;
	}
	public Object getMetadata() {
		return metadata;
	}
	public void setMetadata(Object metadata) {
		this.metadata = metadata;
	}
	public Object getEnd() {
		return end;
	}
	public void setEnd(Object end) {
		this.end = end;
	}
}
