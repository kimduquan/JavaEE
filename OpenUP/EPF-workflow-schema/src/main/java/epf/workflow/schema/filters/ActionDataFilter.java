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
    "fromStateData",
    "results",
    "toStateData",
    "useResults"
})
@Embeddable
public class ActionDataFilter implements Serializable
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * Workflow expression that selects state data that the state action can use
     * 
     */
    @JsonbProperty("fromStateData")
    @Column
    private String fromStateData;
    /**
     * Workflow expression that filters the actions data results
     * 
     */
    @JsonbProperty("results")
    @Column
    private String results;
    /**
     * Workflow expression that selects a state data element to which the action results should be added/merged into. If not specified, denote, the top-level state data element
     * 
     */
    @JsonbProperty("toStateData")
    @Column
    private String toStateData;
    /**
     * If set to false, action data results are not added/merged to state data. In this case 'results' and 'toStateData' should be ignored. Default is true.
     * 
     */
    @JsonbProperty("useResults")
    @Column
    private boolean useResults = true;

    /**
     * Workflow expression that selects state data that the state action can use
     * 
     */
    @JsonbProperty("fromStateData")
    public String getFromStateData() {
        return fromStateData;
    }

    /**
     * Workflow expression that selects state data that the state action can use
     * 
     */
    @JsonbProperty("fromStateData")
    public void setFromStateData(final String fromStateData) {
        this.fromStateData = fromStateData;
    }

    /**
     * Workflow expression that filters the actions data results
     * 
     */
    @JsonbProperty("results")
    public String getResults() {
        return results;
    }

    /**
     * Workflow expression that filters the actions data results
     * 
     */
    @JsonbProperty("results")
    public void setResults(final String results) {
        this.results = results;
    }

    /**
     * Workflow expression that selects a state data element to which the action results should be added/merged into. If not specified, denote, the top-level state data element
     * 
     */
    @JsonbProperty("toStateData")
    public String getToStateData() {
        return toStateData;
    }

    /**
     * Workflow expression that selects a state data element to which the action results should be added/merged into. If not specified, denote, the top-level state data element
     * 
     */
    @JsonbProperty("toStateData")
    public void setToStateData(final String toStateData) {
        this.toStateData = toStateData;
    }

    /**
     * If set to false, action data results are not added/merged to state data. In this case 'results' and 'toStateData' should be ignored. Default is true.
     * 
     */
    @JsonbProperty("useResults")
    public boolean isUseResults() {
        return useResults;
    }

    /**
     * If set to false, action data results are not added/merged to state data. In this case 'results' and 'toStateData' should be ignored. Default is true.
     * 
     */
    @JsonbProperty("useResults")
    public void setUseResults(final boolean useResults) {
        this.useResults = useResults;
    }
}
