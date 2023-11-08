package epf.workflow.schema;

import jakarta.json.bind.annotation.JsonbTypeAdapter;
import epf.workflow.schema.adapter.EndDefinitionAdapter;
import epf.workflow.schema.adapter.TransitionDefinitionAdapter;
import epf.workflow.schema.util.Either;
import jakarta.nosql.Column;
import java.io.Serializable;
import java.util.List;
import org.eclipse.jnosql.mapping.Embeddable;

/**
 * @author PC
 *
 */
@Embeddable
public class ErrorDefinition implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	@Column
	private String errorRef;
	/**
	 * 
	 */
	@Column
	private List<String> errorRefs;
	
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

	public String getErrorRef() {
		return errorRef;
	}

	public void setErrorRef(String errorRef) {
		this.errorRef = errorRef;
	}

	public List<String> getErrorRefs() {
		return errorRefs;
	}

	public void setErrorRefs(List<String> errorRefs) {
		this.errorRefs = errorRefs;
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
}
