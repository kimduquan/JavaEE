package epf.workflow.schema;

/**
 * @author PC
 *
 */
public class SwitchStateConditions {

	/**
	 * 
	 */
	private String name;
	/**
	 * 
	 */
	private Object transition;
	/**
	 * 
	 */
	private Object end;
	/**
	 * 
	 */
	private Object metadata;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public Object getMetadata() {
		return metadata;
	}
	public void setMetadata(Object metadata) {
		this.metadata = metadata;
	}

}
