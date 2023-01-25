package epf.workflow.schema.filters;

import java.io.Serializable;
import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbPropertyOrder;
import jakarta.nosql.mapping.Column;
import jakarta.nosql.mapping.Embeddable;

/**
 * @author PC
 *
 */
@JsonbPropertyOrder({
    "input",
    "output"
})
@Embeddable
public class StateDataFilter implements Serializable
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * Workflow expression to filter the state data input
     * 
     */
    @JsonbProperty("input")
    @Column
    private String input;
    /**
     * Workflow expression that filters the state data output
     * 
     */
    @JsonbProperty("output")
    @Column
    private String output;

    /**
     * Workflow expression to filter the state data input
     * 
     */
    @JsonbProperty("input")
    public String getInput() {
        return input;
    }

    /**
     * Workflow expression to filter the state data input
     * 
     */
    @JsonbProperty("input")
    public void setInput(final String input) {
        this.input = input;
    }

    /**
     * Workflow expression that filters the state data output
     * 
     */
    @JsonbProperty("output")
    public String getOutput() {
        return output;
    }

    /**
     * Workflow expression that filters the state data output
     * 
     */
    @JsonbProperty("output")
    public void setOutput(final String output) {
        this.output = output;
    }
}
