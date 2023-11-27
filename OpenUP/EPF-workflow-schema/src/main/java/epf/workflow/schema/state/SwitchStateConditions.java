package epf.workflow.schema.state;

import jakarta.json.bind.annotation.JsonbTypeAdapter;
import epf.workflow.schema.EndDefinition;
import epf.workflow.schema.TransitionDefinition;
import epf.workflow.schema.adapter.BooleanOrEndDefinitionAdapter;
import epf.workflow.schema.adapter.StringOrTransitionDefinitionAdapter;
import epf.workflow.schema.util.BooleanOrObject;
import epf.workflow.schema.util.StringOrObject;
import jakarta.nosql.Column;
import java.io.Serializable;
import org.eclipse.jnosql.mapping.MappedSuperclass;

/**
 * @author PC
 *
 */
@MappedSuperclass
public class SwitchStateConditions implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	@Column
	private String name;
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
	@Column
	private Object metadata;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public Object getMetadata() {
		return metadata;
	}
	public void setMetadata(Object metadata) {
		this.metadata = metadata;
	}

}
