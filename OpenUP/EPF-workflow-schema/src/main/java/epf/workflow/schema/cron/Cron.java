package epf.workflow.schema.cron;

import java.io.Serializable;
import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbPropertyOrder;
import javax.validation.constraints.NotNull;

/**
 * @author PC
 *
 */
@JsonbPropertyOrder({
    "expression",
    "validUntil"
})
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
    private String expression;
    /**
     * Specific date and time (ISO 8601 format) when the cron expression invocation is no longer valid
     * 
     */
    @JsonbProperty("validUntil")
    private String validUntil;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Cron() {
    }

    /**
     * 
     * @param expression
     */
    public Cron(String expression) {
        super();
        this.expression = expression;
    }

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
    public void setExpression(String expression) {
        this.expression = expression;
    }

    public Cron withExpression(String expression) {
        this.expression = expression;
        return this;
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
    public void setValidUntil(String validUntil) {
        this.validUntil = validUntil;
    }

    public Cron withValidUntil(String validUntil) {
        this.validUntil = validUntil;
        return this;
    }

}