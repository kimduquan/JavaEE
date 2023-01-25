package epf.workflow.schema.sleep;

import java.io.Serializable;
import javax.validation.constraints.NotNull;
import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbPropertyOrder;

/**
 * @author PC
 *
 */
@JsonbPropertyOrder({
    "before",
    "after"
})
public class Sleep implements Serializable
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * Amount of time (ISO 8601 duration format) to sleep before function/subflow invocation. Does not apply if 'eventRef' is defined.
     * (Required)
     * 
     */
    @JsonbProperty("before")
    @NotNull
    private String before;
    /**
     * Amount of time (ISO 8601 duration format) to sleep after function/subflow invocation. Does not apply if 'eventRef' is defined.
     * (Required)
     * 
     */
    @JsonbProperty("after")
    @NotNull
    private String after;

    /**
     * Amount of time (ISO 8601 duration format) to sleep before function/subflow invocation. Does not apply if 'eventRef' is defined.
     * (Required)
     * 
     */
    @JsonbProperty("before")
    public String getBefore() {
        return before;
    }

    /**
     * Amount of time (ISO 8601 duration format) to sleep before function/subflow invocation. Does not apply if 'eventRef' is defined.
     * (Required)
     * 
     */
    @JsonbProperty("before")
    public void setBefore(final String before) {
        this.before = before;
    }

    /**
     * Amount of time (ISO 8601 duration format) to sleep after function/subflow invocation. Does not apply if 'eventRef' is defined.
     * (Required)
     * 
     */
    @JsonbProperty("after")
    public String getAfter() {
        return after;
    }

    /**
     * Amount of time (ISO 8601 duration format) to sleep after function/subflow invocation. Does not apply if 'eventRef' is defined.
     * (Required)
     * 
     */
    @JsonbProperty("after")
    public void setAfter(final String after) {
        this.after = after;
    }
}
