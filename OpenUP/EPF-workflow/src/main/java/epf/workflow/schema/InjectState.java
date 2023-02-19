package epf.workflow.schema;

import javax.json.JsonObject;
import javax.json.bind.annotation.JsonbTypeAdapter;
import javax.validation.constraints.NotNull;

import epf.workflow.schema.adapter.EndDefinitionAdapter;
import epf.workflow.schema.adapter.TransitionDefinitionAdapter;
import jakarta.nosql.mapping.Column;
import jakarta.nosql.mapping.Embeddable;

/**
 * @author PC
 *
 */
@Embeddable
public class InjectState extends State {

	/**
	 * 
	 */
	@NotNull
	@Column
	private JsonObject data;
	/**
	 * 
	 */
	@Column
	private StateDataFilters stateDataFilter;
	/**
	 * 
	 */
	@Column
	@JsonbTypeAdapter(value = TransitionDefinitionAdapter.class)
	private Object transition;
	/**
	 * 
	 */
	@Column
	private String compensatedBy;
	/**
	 * 
	 */
	@Column
	private boolean usedForCompensation;
	/**
	 * 
	 */
	@Column
	private Object metadata;
	/**
	 * 
	 */
	@Column
	@JsonbTypeAdapter(value = EndDefinitionAdapter.class)
	private Object end;
	
	public JsonObject getData() {
		return data;
	}
	public void setData(JsonObject data) {
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
