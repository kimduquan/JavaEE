package epf.workflow.schema;

import javax.json.bind.annotation.JsonbTypeAdapter;
import epf.workflow.schema.adapter.EndDefinitionAdapter;
import epf.workflow.schema.adapter.TransitionDefinitionAdapter;
import jakarta.nosql.mapping.Column;
import jakarta.nosql.mapping.Embeddable;

/**
 * @author PC
 *
 */
@Embeddable
public class ErrorDefinition {

	/**
	 * 
	 */
	@Column
	private String errorRef;
	/**
	 * 
	 */
	@Column
	private String[] errorRefs;
	
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

	public String getErrorRef() {
		return errorRef;
	}

	public void setErrorRef(String errorRef) {
		this.errorRef = errorRef;
	}

	public String[] getErrorRefs() {
		return errorRefs;
	}

	public void setErrorRefs(String[] errorRefs) {
		this.errorRefs = errorRefs;
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