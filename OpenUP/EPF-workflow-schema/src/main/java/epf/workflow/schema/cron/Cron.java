package epf.workflow.schema.cron;

import java.io.Serializable;
import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbPropertyOrder;
import javax.validation.constraints.NotNull;
import jakarta.nosql.mapping.Column;
import jakarta.nosql.mapping.Embeddable;

/**
 * @author PC
 *
 */
@JsonbPropertyOrder({
    "expression",
    "validUntil"
})
@Embeddable
public class Cron implements Serializable
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * Repeating interval (cron expression) describing when the workflow instance should be created
     * (Required)
     * 
     */
    @JsonbProperty("expression")
    @NotNull
    @Column
    private String expression;
    /**
     * Specific date and time (ISO 8601 format) when the cron expression invocation is no longer valid
     * 
     */
    @JsonbProperty("validUntil")
    @Column
    private String validUntil;

    /**
     * Repeating interval (cron expression) describing when the workflow instance should be created
     * (Required)
     * 
     */
    @JsonbProperty("expression")
    public String getExpression() {
        return expression;
    }

    /**
     * Repeating interval (cron expression) describing when the workflow instance should be created
     * (Required)
     * 
     */
    @JsonbProperty("expression")
    public void setExpression(final String expression) {
        this.expression = expression;
    }

    /**
     * Specific date and time (ISO 8601 format) when the cron expression invocation is no longer valid
     * 
     */
    @JsonbProperty("validUntil")
    public String getValidUntil() {
        return validUntil;
    }

    /**
     * Specific date and time (ISO 8601 format) when the cron expression invocation is no longer valid
     * 
     */
    @JsonbProperty("validUntil")
    public void setValidUntil(final String validUntil) {
        this.validUntil = validUntil;
    }
}