package epf.workflow.schema.filters;

import java.io.Serializable;
import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbPropertyOrder;

/**
 * @author PC
 *
 */
@JsonbPropertyOrder({
    "data",
    "toStateData",
    "useData"
})
public class EventDataFilter implements Serializable
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * Workflow expression that filters of the event data (payload)
     * 
     */
    @JsonbProperty("data")
    private String data;
    /**
     *  Workflow expression that selects a state data element to which the event payload should be added/merged into. If not specified, denotes, the top-level state data element.
     * 
     */
    @JsonbProperty("toStateData")
    private String toStateData;
    /**
     * If set to false, event payload is not added/merged to state data. In this case 'data' and 'toStateData' should be ignored. Default is true.
     * 
     */
    @JsonbProperty("useData")
    private boolean useData = true;

    /**
     * Workflow expression that filters of the event data (payload)
     * 
     */
    @JsonbProperty("data")
    public String getData() {
        return data;
    }

    /**
     * Workflow expression that filters of the event data (payload)
     * 
     */
    @JsonbProperty("data")
    public void setData(final String data) {
        this.data = data;
    }

    /**
     *  Workflow expression that selects a state data element to which the event payload should be added/merged into. If not specified, denotes, the top-level state data element.
     * 
     */
    @JsonbProperty("toStateData")
    public String getToStateData() {
        return toStateData;
    }

    /**
     *  Workflow expression that selects a state data element to which the event payload should be added/merged into. If not specified, denotes, the top-level state data element.
     * 
     */
    @JsonbProperty("toStateData")
    public void setToStateData(final String toStateData) {
        this.toStateData = toStateData;
    }

    /**
     * If set to false, event payload is not added/merged to state data. In this case 'data' and 'toStateData' should be ignored. Default is true.
     * 
     */
    @JsonbProperty("useData")
    public boolean isUseData() {
        return useData;
    }

    /**
     * If set to false, event payload is not added/merged to state data. In this case 'data' and 'toStateData' should be ignored. Default is true.
     * 
     */
    @JsonbProperty("useData")
    public void setUseData(final boolean useData) {
        this.useData = useData;
    }
}