package epf.workflow.schema.state;

import jakarta.json.bind.annotation.JsonbTypeAdapter;
import epf.workflow.schema.EndDefinition;
import epf.workflow.schema.TransitionDefinition;
import epf.workflow.schema.adapter.EndDefinitionAdapter;
import epf.workflow.schema.adapter.TransitionDefinitionAdapter;
import epf.workflow.schema.util.Either;
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
	@JsonbTypeAdapter(value = TransitionDefinitionAdapter.class)
	private Either<String, TransitionDefinition> transition;
	/**
	 * 
	 */
	@Column
	@JsonbTypeAdapter(value = EndDefinitionAdapter.class)
	private Either<Boolean, EndDefinition> end;
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
	public Either<String, TransitionDefinition> getTransition() {
		return transition;
	}
	public void setTransition(Either<String, TransitionDefinition> transition) {
		this.transition = transition;
	}
	public Either<Boolean, EndDefinition> getEnd() {
		return end;
	}
	public void setEnd(Either<Boolean, EndDefinition> end) {
		this.end = end;
	}
	public Object getMetadata() {
		return metadata;
	}
	public void setMetadata(Object metadata) {
		this.metadata = metadata;
	}

}
