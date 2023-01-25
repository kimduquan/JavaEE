package epf.workflow.schema.timeouts;

import java.io.Serializable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbPropertyOrder;

/**
 * @author PC
 *
 */
@JsonbPropertyOrder({
    "single",
    "total"
})
public class StateExecTimeout implements Serializable
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * Single state execution timeout, not including retries (ISO 8601 duration format)
     * 
     */
    @JsonbProperty("single")
    @Size(min = 1)
    private String single;
    /**
     * Total state execution timeout, including retries (ISO 8601 duration format)
     * (Required)
     * 
     */
    @JsonbProperty("total")
    @Size(min = 1)
    @NotNull
    private String total;

    /**
     * Single state execution timeout, not including retries (ISO 8601 duration format)
     * 
     */
    @JsonbProperty("single")
    public String getSingle() {
        return single;
    }

    /**
     * Single state execution timeout, not including retries (ISO 8601 duration format)
     * 
     */
    @JsonbProperty("single")
    public void setSingle(final String single) {
        this.single = single;
    }

    /**
     * Total state execution timeout, including retries (ISO 8601 duration format)
     * (Required)
     * 
     */
    @JsonbProperty("total")
    public String getTotal() {
        return total;
    }

    /**
     * Total state execution timeout, including retries (ISO 8601 duration format)
     * (Required)
     * 
     */
    @JsonbProperty("total")
    public void setTotal(final String total) {
        this.total = total;
    }
}
