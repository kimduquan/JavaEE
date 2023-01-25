package epf.workflow.schema.actions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbPropertyOrder;
import javax.validation.Valid;
import javax.validation.constraints.Size;

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
    private String id;
    /**
     * Unique action definition name
     * 
     */
    @JsonbProperty("name")
    private String name;
    @JsonbProperty("functionRef")
    @Valid
    private FunctionRef functionRef;
    /**
     * Event References
     * 
     */
    @JsonbProperty("eventRef")
    @Valid
    private EventRef eventRef;
    @JsonbProperty("subFlowRef")
    @Valid
    private SubFlowRef subFlowRef;
    @JsonbProperty("sleep")
    @Valid
    private Sleep sleep;
    /**
     * References a defined workflow retry definition. If not defined the default retry policy is assumed
     * 
     */
    @JsonbProperty("retryRef")
    private String retryRef;
    /**
     * List of unique references to defined workflow errors for which the action should not be retried. Used only when `autoRetries` is set to `true`
     * 
     */
    @JsonbProperty("nonRetryableErrors")
    @Size(min = 1)
    @Valid
    private List<String> nonRetryableErrors = new ArrayList<String>();
    /**
     * List of unique references to defined workflow errors for which the action should be retried. Used only when `autoRetries` is set to `false`
     * 
     */
    @JsonbProperty("retryableErrors")
    @Size(min = 1)
    @Valid
    private List<String> retryableErrors = new ArrayList<String>();
    @JsonbProperty("actionDataFilter")
    @Valid
    private ActionDataFilter actionDataFilter;
    /**
     * Expression, if defined, must evaluate to true for this action to be performed. If false, action is disregarded
     * 
     */
    @JsonbProperty("condition")
    @Size(min = 1)
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
    public void setId(String id) {
        this.id = id;
    }

    public Action withId(String id) {
        this.id = id;
        return this;
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
    public void setName(String name) {
        this.name = name;
    }

    public Action withName(String name) {
        this.name = name;
        return this;
    }

    @JsonbProperty("functionRef")
    public FunctionRef getFunctionRef() {
        return functionRef;
    }

    @JsonbProperty("functionRef")
    public void setFunctionRef(FunctionRef functionRef) {
        this.functionRef = functionRef;
    }

    public Action withFunctionRef(FunctionRef functionRef) {
        this.functionRef = functionRef;
        return this;
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
    public void setEventRef(EventRef eventRef) {
        this.eventRef = eventRef;
    }

    public Action withEventRef(EventRef eventRef) {
        this.eventRef = eventRef;
        return this;
    }

    @JsonbProperty("subFlowRef")
    public SubFlowRef getSubFlowRef() {
        return subFlowRef;
    }

    @JsonbProperty("subFlowRef")
    public void setSubFlowRef(SubFlowRef subFlowRef) {
        this.subFlowRef = subFlowRef;
    }

    public Action withSubFlowRef(SubFlowRef subFlowRef) {
        this.subFlowRef = subFlowRef;
        return this;
    }

    @JsonbProperty("sleep")
    public Sleep getSleep() {
        return sleep;
    }

    @JsonbProperty("sleep")
    public void setSleep(Sleep sleep) {
        this.sleep = sleep;
    }

    public Action withSleep(Sleep sleep) {
        this.sleep = sleep;
        return this;
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
    public void setRetryRef(String retryRef) {
        this.retryRef = retryRef;
    }

    public Action withRetryRef(String retryRef) {
        this.retryRef = retryRef;
        return this;
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
    public void setNonRetryableErrors(List<String> nonRetryableErrors) {
        this.nonRetryableErrors = nonRetryableErrors;
    }

    public Action withNonRetryableErrors(List<String> nonRetryableErrors) {
        this.nonRetryableErrors = nonRetryableErrors;
        return this;
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
    public void setRetryableErrors(List<String> retryableErrors) {
        this.retryableErrors = retryableErrors;
    }

    public Action withRetryableErrors(List<String> retryableErrors) {
        this.retryableErrors = retryableErrors;
        return this;
    }

    @JsonbProperty("actionDataFilter")
    public ActionDataFilter getActionDataFilter() {
        return actionDataFilter;
    }

    @JsonbProperty("actionDataFilter")
    public void setActionDataFilter(ActionDataFilter actionDataFilter) {
        this.actionDataFilter = actionDataFilter;
    }

    public Action withActionDataFilter(ActionDataFilter actionDataFilter) {
        this.actionDataFilter = actionDataFilter;
        return this;
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
    public void setCondition(String condition) {
        this.condition = condition;
    }

    public Action withCondition(String condition) {
        this.condition = condition;
        return this;
    }
}
