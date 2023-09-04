package epf.workflow.schema.state;

import jakarta.nosql.Column;
import org.eclipse.jnosql.mapping.Embeddable;

/**
 * @author PC
 *
 */
@Embeddable
public class StateDataFilters {

	/**
	 * 
	 */
	@Column
	private String input;
	/**
	 * 
	 */
	@Column
	private String output;
	
	public String getInput() {
		return input;
	}
	public void setInput(String input) {
		this.input = input;
	}
	public String getOutput() {
		return output;
	}
	public void setOutput(String output) {
		this.output = output;
	}
}
