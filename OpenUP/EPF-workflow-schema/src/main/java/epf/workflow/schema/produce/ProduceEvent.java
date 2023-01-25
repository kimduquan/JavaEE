package epf.workflow.schema.produce;

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
    "eventRef",
    "data"
})
public class ProduceEvent implements Serializable
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * References a name of a defined event
     * (Required)
     * 
     */
    @JsonbProperty("eventRef")
    @Size(min = 1)
    @NotNull
    private String eventRef;
    /**
     * Workflow expression which selects parts of the states data output to become the data of the produced event
     * 
     */
    @JsonbProperty("data")
    private String data;

    /**
     * References a name of a defined event
     * (Required)
     * 
     */
    @JsonbProperty("eventRef")
    public String getEventRef() {
        return eventRef;
    }

    /**
     * References a name of a defined event
     * (Required)
     * 
     */
    @JsonbProperty("eventRef")
    public void setEventRef(final String eventRef) {
        this.eventRef = eventRef;
    }

    /**
     * Workflow expression which selects parts of the states data output to become the data of the produced event
     * 
     */
    @JsonbProperty("data")
    public String getData() {
        return data;
    }

    /**
     * Workflow expression which selects parts of the states data output to become the data of the produced event
     * 
     */
    @JsonbProperty("data")
    public void setData(final String data) {
        this.data = data;
    }
}
