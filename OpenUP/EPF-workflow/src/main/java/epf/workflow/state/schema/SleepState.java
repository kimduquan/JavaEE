package epf.workflow.state.schema;

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
public class SleepState extends State {

	/**
	 * 
	 */
	@NotNull
	@Column
	private String duration;
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
	@JsonbTypeAdapter(value = EndDefinitionAdapter.class)
	private Object end;
	
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public Object getTransition() {
		return transition;
	}
	public void setTransition(Object transition) {
		this.transition = transition;
	}
	public Object getEnd() {
		return end;
	}
	public void setEnd(Object end) {
		this.end = end;
	}
}
