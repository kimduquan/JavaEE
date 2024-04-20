package epf.workflow.schema.action;

import jakarta.json.bind.annotation.JsonbTypeAdapter;
import epf.workflow.schema.event.EventRefDefinition;
import epf.workflow.schema.function.FunctionRefDefinition;
import epf.workflow.schema.function.adapter.StringOrFunctionRefDefinitionAdapter;
import epf.nosql.schema.StringOrObject;
import epf.workflow.schema.SubFlowRefDefinition;
import epf.workflow.schema.WorkflowError;
import epf.workflow.schema.adapter.StringOrSubFlowRefDefinitionAdapter;
import jakarta.nosql.Column;
import java.io.Serializable;
import java.util.List;
import org.eclipse.jnosql.mapping.Embeddable;

/**
 * @author PC
 *
 */
@Embeddable
public class ActionDefinition implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@Column
	private String name;
	
	/**
	 * 
	 */
	@Column
	@JsonbTypeAdapter(value = StringOrFunctionRefDefinitionAdapter.class)
	private StringOrObject<FunctionRefDefinition> functionRef;
	
	/**
	 * 
	 */
	@Column
	private EventRefDefinition eventRef;
	
	/**
	 * 
	 */
	@Column
	@JsonbTypeAdapter(value = StringOrSubFlowRefDefinitionAdapter.class)
	private StringOrObject<SubFlowRefDefinition> subFlowRef;
	
	/**
	 * 
	 */
	@Column
	private String retryRef;
	
	/**
	 * 
	 */
	@Column
	private List<WorkflowError> nonRetryableErrors;
	
	/**
	 * 
	 */
	@Column
	private List<WorkflowError> retryableErrors;
	
	/**
	 * 
	 */
	@Column
	private ActionDataFilter actionDataFilter;
	
	/**
	 * 
	 */
	@Column
	private Sleep sleep;
	
	/**
	 * 
	 */
	@Column
	private String condition;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public StringOrObject<FunctionRefDefinition> getFunctionRef() {
		return functionRef;
	}

	public void setFunctionRef(StringOrObject<FunctionRefDefinition> functionRef) {
		this.functionRef = functionRef;
	}

	public EventRefDefinition getEventRef() {
		return eventRef;
	}

	public void setEventRef(EventRefDefinition eventRef) {
		this.eventRef = eventRef;
	}

	public StringOrObject<SubFlowRefDefinition> getSubFlowRef() {
		return subFlowRef;
	}

	public void setSubFlowRef(StringOrObject<SubFlowRefDefinition> subFlowRef) {
		this.subFlowRef = subFlowRef;
	}

	public String getRetryRef() {
		return retryRef;
	}

	public void setRetryRef(String retryRef) {
		this.retryRef = retryRef;
	}

	public List<WorkflowError> getNonRetryableErrors() {
		return nonRetryableErrors;
	}

	public void setNonRetryableErrors(List<WorkflowError> nonRetryableErrors) {
		this.nonRetryableErrors = nonRetryableErrors;
	}

	public List<WorkflowError> getRetryableErrors() {
		return retryableErrors;
	}

	public void setRetryableErrors(List<WorkflowError> retryableErrors) {
		this.retryableErrors = retryableErrors;
	}

	public ActionDataFilter getActionDataFilter() {
		return actionDataFilter;
	}

	public void setActionDataFilter(ActionDataFilter actionDataFilter) {
		this.actionDataFilter = actionDataFilter;
	}

	public Sleep getSleep() {
		return sleep;
	}

	public void setSleep(Sleep sleep) {
		this.sleep = sleep;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}
}
