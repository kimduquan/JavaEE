package epf.workflow;

import java.util.LinkedHashMap;

/**
 * @author PC
 *
 */
public class WorkflowData extends LinkedHashMap<String, Object> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public Object getInput() {
		return get("input");
	}
	public void setInput(final Object input) {
		put("input", input);
	}
	public Object getOutput() {
		return get("output");
	}
	public void setOutput(final Object output) {
		put("output", output);
	}
}
