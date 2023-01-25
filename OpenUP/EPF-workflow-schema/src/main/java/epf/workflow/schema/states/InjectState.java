package epf.workflow.schema.states;

import javax.validation.Valid;
import jakarta.nosql.mapping.Column;
import jakarta.nosql.mapping.Embeddable;
import javax.json.JsonValue;
import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbPropertyOrder;

/**
 * @author PC
 *
 */
@JsonbPropertyOrder({
    "data",
    "usedForCompensation"
})
@Embeddable
public class InjectState extends DefaultState
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * JSON object which can be set as states data input and can be manipulated via filters
     * 
     */
    @JsonbProperty("data")
    @Valid
    @Column
    private JsonValue data;
    /**
     * If true, this state is used to compensate another state. Default is false
     * 
     */
    @JsonbProperty("usedForCompensation")
    @Column
    private boolean usedForCompensation = false;

    /**
     * JSON object which can be set as states data input and can be manipulated via filters
     * 
     */
    @JsonbProperty("data")
    public JsonValue getData() {
        return data;
    }

    /**
     * JSON object which can be set as states data input and can be manipulated via filters
     * 
     */
    @JsonbProperty("data")
    public void setData(final JsonValue data) {
        this.data = data;
    }

    /**
     * If true, this state is used to compensate another state. Default is false
     * 
     */
    @JsonbProperty("usedForCompensation")
    public boolean isUsedForCompensation() {
        return usedForCompensation;
    }

    /**
     * If true, this state is used to compensate another state. Default is false
     * 
     */
    @JsonbProperty("usedForCompensation")
    public void setUsedForCompensation(final boolean usedForCompensation) {
        this.usedForCompensation = usedForCompensation;
    }
}
