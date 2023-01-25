package epf.workflow.schema.actions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbPropertyOrder;
import javax.validation.Valid;
import javax.validation.constraints.Size;
import epf.workflow.schema.events.EventRef;
import epf.workflow.schema.filters.ActionDataFilter;
import epf.workflow.schema.functions.FunctionRef;
import epf.workflow.schema.functions.SubFlowRef;
import epf.workflow.schema.sleep.Sleep;
import jakarta.nosql.mapping.Column;
import jakarta.nosql.mapping.Entity;
import jakarta.nosql.mapping.Id;

/**
 * @author PC
 *
 */
@JsonbPropertyOrder({
    "id",
    "name",
    "functionRef",
    "eventRef",
    "subFlowRef",
    "sleep",
    "retryRef",
    "nonRetryableErrors",
    "retryableErrors",
    "actionDataFilter",
    "condition"
})
@Entity
public class Action implements Serializable
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * Unique action identifier
     * 
     */
    @JsonbProperty("id")
    @Column
    @Id
    private String id;
    /**
     * Unique action definition name
     * 
     */
    @JsonbProperty("name")
    @Column
    private String name;
    /**
     * 
     */
    @JsonbProperty("functionRef")
    @Valid
    @Column
    private FunctionRef functionRef;
    /**
     * Event References
     * 
     */
    @JsonbProperty("eventRef")
    @Valid
    @Column
    private EventRef eventRef;
    /**
     * 
     */
    @JsonbProperty("subFlowRef")
    @Valid
    @Column
    private SubFlowRef subFlowRef;
    /**
     * 
     */
    @JsonbProperty("sleep")
    @Valid
    @Column
    private Sleep sleep;
    /**
     * References a defined workflow retry definition. If not defined the default retry policy is assumed
     * 
     */
    @JsonbProperty("retryRef")
    @Column
    private String retryRef;
    /**
     * List of unique references to defined workflow errors for which the action should not be retried. Used only when `autoRetries` is set to `true`
     * 
     */
    @JsonbProperty("nonRetryableErrors")
    @Size(min = 1)
    @Valid
    @Column
    private List<String> nonRetryableErrors = new ArrayList<String>();
    /**
     * List of unique references to defined workflow errors for which the action should be retried. Used only when `autoRetries` is set to `false`
     * 
     */
    @JsonbProperty("retryableErrors")
    @Size(min = 1)
    @Valid
    @Column
    private List<String> retryableErrors = new ArrayList<String>();
    /**
     * 
     */
    @JsonbProperty("actionDataFilter")
    @Valid
    @Column
    private ActionDataFilter actionDataFilter;
    /**
     * Expression, if defined, must evaluate to true for this action to be performed. If false, action is disregarded
     * 
     */
    @JsonbProperty("condition")
    @Size(min = 1)
    @Column
    private String condition;

    /**
     * Unique action identifier
     * 
     */
    @JsonbProperty("id")
    public String getId() {
        return id;
    }

    /**
     * Unique action identifier
     * 
     */
    @JsonbProperty("id")
    public void setId(final String id) {
        this.id = id;
    }

    /**
     * Unique action definition name
     * 
     */
    @JsonbProperty("name")
    public String getName() {
        return name;
    }

    /**
     * Unique action definition name
     * 
     */
    @JsonbProperty("name")
    public void setName(final String name) {
        this.name = name;
    }

    @JsonbProperty("functionRef")
    public FunctionRef getFunctionRef() {
        return functionRef;
    }

    @JsonbProperty("functionRef")
    public void setFunctionRef(final FunctionRef functionRef) {
        this.functionRef = functionRef;
    }

    /**
     * Event References
     * 
     */
    @JsonbProperty("eventRef")
    public EventRef getEventRef() {
        return eventRef;
    }

    /**
     * Event References
     * 
     */
    @JsonbProperty("eventRef")
    public void setEventRef(final EventRef eventRef) {
        this.eventRef = eventRef;
    }

    @JsonbProperty("subFlowRef")
    public SubFlowRef getSubFlowRef() {
        return subFlowRef;
    }

    @JsonbProperty("subFlowRef")
    public void setSubFlowRef(final SubFlowRef subFlowRef) {
        this.subFlowRef = subFlowRef;
    }

    @JsonbProperty("sleep")
    public Sleep getSleep() {
        return sleep;
    }

    @JsonbProperty("sleep")
    public void setSleep(final Sleep sleep) {
        this.sleep = sleep;
    }

    /**
     * References a defined workflow retry definition. If not defined the default retry policy is assumed
     * 
     */
    @JsonbProperty("retryRef")
    public String getRetryRef() {
        return retryRef;
    }

    /**
     * References a defined workflow retry definition. If not defined the default retry policy is assumed
     * 
     */
    @JsonbProperty("retryRef")
    public void setRetryRef(final String retryRef) {
        this.retryRef = retryRef;
    }

    /**
     * List of unique references to defined workflow errors for which the action should not be retried. Used only when `autoRetries` is set to `true`
     * 
     */
    @JsonbProperty("nonRetryableErrors")
    public List<String> getNonRetryableErrors() {
        return nonRetryableErrors;
    }

    /**
     * List of unique references to defined workflow errors for which the action should not be retried. Used only when `autoRetries` is set to `true`
     * 
     */
    @JsonbProperty("nonRetryableErrors")
    public void setNonRetryableErrors(final List<String> nonRetryableErrors) {
        this.nonRetryableErrors = nonRetryableErrors;
    }

    /**
     * List of unique references to defined workflow errors for which the action should be retried. Used only when `autoRetries` is set to `false`
     * 
     */
    @JsonbProperty("retryableErrors")
    public List<String> getRetryableErrors() {
        return retryableErrors;
    }

    /**
     * List of unique references to defined workflow errors for which the action should be retried. Used only when `autoRetries` is set to `false`
     * 
     */
    @JsonbProperty("retryableErrors")
    public void setRetryableErrors(final List<String> retryableErrors) {
        this.retryableErrors = retryableErrors;
    }

    @JsonbProperty("actionDataFilter")
    public ActionDataFilter getActionDataFilter() {
        return actionDataFilter;
    }

    @JsonbProperty("actionDataFilter")
    public void setActionDataFilter(final ActionDataFilter actionDataFilter) {
        this.actionDataFilter = actionDataFilter;
    }

    /**
     * Expression, if defined, must evaluate to true for this action to be performed. If false, action is disregarded
     * 
     */
    @JsonbProperty("condition")
    public String getCondition() {
        return condition;
    }

    /**
     * Expression, if defined, must evaluate to true for this action to be performed. If false, action is disregarded
     * 
     */
    @JsonbProperty("condition")
    public void setCondition(final String condition) {
        this.condition = condition;
    }
}
