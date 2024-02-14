package epf.workflow.schema.state;

import jakarta.json.bind.annotation.JsonbTypeAdapter;
import jakarta.validation.constraints.NotNull;
import epf.nosql.schema.BooleanOrObject;
import epf.nosql.schema.StringOrObject;
import epf.workflow.schema.EndDefinition;
import epf.workflow.schema.TransitionDefinition;
import epf.workflow.schema.adapter.BooleanOrEndDefinitionAdapter;
import epf.workflow.schema.adapter.StringOrTransitionDefinitionAdapter;
import jakarta.nosql.Column;
import org.eclipse.jnosql.mapping.DiscriminatorValue;
import org.eclipse.jnosql.mapping.Embeddable;

/**
 * @author PC
 *
 */
@Embeddable
@DiscriminatorValue(Type.SLEEP)
public class SleepState extends State {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
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
	@JsonbTypeAdapter(value = StringOrTransitionDefinitionAdapter.class)
	private StringOrObject<TransitionDefinition> transition;
	/**
	 * 
	 */
	@Column
	@JsonbTypeAdapter(value = BooleanOrEndDefinitionAdapter.class)
	private BooleanOrObject<EndDefinition> end;
	
	/**
	 * 
	 */
	public SleepState() {
		setType_(Type.sleep);
	}
	
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public StringOrObject<TransitionDefinition> getTransition() {
		return transition;
	}
	public void setTransition(StringOrObject<TransitionDefinition> transition) {
		this.transition = transition;
	}
	public BooleanOrObject<EndDefinition> getEnd() {
		return end;
	}
	public void setEnd(BooleanOrObject<EndDefinition> end) {
		this.end = end;
	}
}
