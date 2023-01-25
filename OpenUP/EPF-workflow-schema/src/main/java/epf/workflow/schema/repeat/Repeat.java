package epf.workflow.schema.repeat;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Size;
import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbPropertyOrder;

/**
 * @author PC
 *
 */
@JsonbPropertyOrder({
    "expression",
    "checkBefore",
    "max",
    "continueOnError",
    "stopOnEvents"
})
public class Repeat implements Serializable
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * Expression evaluated against SubFlow state data. SubFlow will repeat execution as long as this expression is true or until the max property count is reached
     * 
     */
    @JsonbProperty("expression")
    @Size(min = 1)
    private String expression;
    /**
     * If true, the expression is evaluated before each repeat execution, if false the expression is evaluated after each repeat execution
     * 
     */
    @JsonbProperty("checkBefore")
    private boolean checkBefore = true;
    /**
     * Sets the maximum amount of repeat executions
     * 
     */
    @JsonbProperty("max")
    @DecimalMin("0")
    private int max;
    /**
     * If true, repeats executions in a case unhandled errors propagate from the sub-workflow to this state
     * 
     */
    @JsonbProperty("continueOnError")
    private boolean continueOnError = false;
    /**
     * List referencing defined consumed workflow events. SubFlow will repeat execution until one of the defined events is consumed, or until the max property count is reached
     * 
     */
    @JsonbProperty("stopOnEvents")
    @Valid
    private List<String> stopOnEvents = new ArrayList<String>();

    /**
     * Expression evaluated against SubFlow state data. SubFlow will repeat execution as long as this expression is true or until the max property count is reached
     * 
     */
    @JsonbProperty("expression")
    public String getExpression() {
        return expression;
    }

    /**
     * Expression evaluated against SubFlow state data. SubFlow will repeat execution as long as this expression is true or until the max property count is reached
     * 
     */
    @JsonbProperty("expression")
    public void setExpression(final String expression) {
        this.expression = expression;
    }

    /**
     * If true, the expression is evaluated before each repeat execution, if false the expression is evaluated after each repeat execution
     * 
     */
    @JsonbProperty("checkBefore")
    public boolean isCheckBefore() {
        return checkBefore;
    }

    /**
     * If true, the expression is evaluated before each repeat execution, if false the expression is evaluated after each repeat execution
     * 
     */
    @JsonbProperty("checkBefore")
    public void setCheckBefore(final boolean checkBefore) {
        this.checkBefore = checkBefore;
    }
    
    /**
     * Sets the maximum amount of repeat executions
     * 
     */
    @JsonbProperty("max")
    public int getMax() {
        return max;
    }

    /**
     * Sets the maximum amount of repeat executions
     * 
     */
    @JsonbProperty("max")
    public void setMax(final int max) {
        this.max = max;
    }

    /**
     * If true, repeats executions in a case unhandled errors propagate from the sub-workflow to this state
     * 
     */
    @JsonbProperty("continueOnError")
    public boolean isContinueOnError() {
        return continueOnError;
    }

    /**
     * If true, repeats executions in a case unhandled errors propagate from the sub-workflow to this state
     * 
     */
    @JsonbProperty("continueOnError")
    public void setContinueOnError(final boolean continueOnError) {
        this.continueOnError = continueOnError;
    }

    /**
     * List referencing defined consumed workflow events. SubFlow will repeat execution until one of the defined events is consumed, or until the max property count is reached
     * 
     */
    @JsonbProperty("stopOnEvents")
    public List<String> getStopOnEvents() {
        return stopOnEvents;
    }

    /**
     * List referencing defined consumed workflow events. SubFlow will repeat execution until one of the defined events is consumed, or until the max property count is reached
     * 
     */
    @JsonbProperty("stopOnEvents")
    public void setStopOnEvents(final List<String> stopOnEvents) {
        this.stopOnEvents = stopOnEvents;
    }
}
