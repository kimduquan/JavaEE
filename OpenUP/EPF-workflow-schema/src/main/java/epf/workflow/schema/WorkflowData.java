package epf.workflow.schema;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import org.eclipse.jnosql.mapping.Embeddable;
import jakarta.nosql.Column;

/**
 * @author PC
 *
 */
@Embeddable
public class WorkflowData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	@Column
	private Map<String, Object> input = new HashMap<>();
	/**
	 * 
	 */
	@Column
	private Map<String, Object> output = new HashMap<>();
	
	public Map<String, Object> getInput() {
		return input;
	}
	public void setInput(final Map<String, Object> input) {
		this.input = input;
	}
	public Map<String, Object> getOutput() {
		return output;
	}
	public void setOutput(final Map<String, Object> output) {
		this.output = output;
	}
}
