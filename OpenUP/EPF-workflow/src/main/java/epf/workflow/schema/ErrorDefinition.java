package epf.workflow.schema;

/**
 * @author PC
 *
 */
public class ErrorDefinition {

	/**
	 * 
	 */
	private String errorRef;
	/**
	 * 
	 */
	private Object[] errorRefs;
	
	/**
	 * 
	 */
	private Object transition;
	
	/**
	 * 
	 */
	private Object end;

	public String getErrorRef() {
		return errorRef;
	}

	public void setErrorRef(String errorRef) {
		this.errorRef = errorRef;
	}

	public Object[] getErrorRefs() {
		return errorRefs;
	}

	public void setErrorRefs(Object[] errorRefs) {
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
