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
	private String[] errorRefs;
	
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
