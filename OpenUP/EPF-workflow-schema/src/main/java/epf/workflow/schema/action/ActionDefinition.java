package epf.workflow.schema.action;

import jakarta.json.bind.annotation.JsonbTypeAdapter;
import epf.workflow.schema.event.EventRefDefinition;
import epf.workflow.schema.function.adapter.FunctionRefDefinitionAdapter;
import epf.workflow.schema.WorkflowError;
import epf.workflow.schema.adapter.SubFlowRefDefinitionAdapter;
import jakarta.nosql.Column;
import org.eclipse.jnosql.mapping.Embeddable;

/**
 * @author PC
 *
 */
@Embeddable
public class ActionDefinition {

	/**
	 * 
	 */
	@Column
	private String name;
	
	/**
	 * 
	 */
	@Column
	@JsonbTypeAdapter(value = FunctionRefDefinitionAdapter.class)
	private Object functionRef;
	
	/**
	 * 
	 */
	@Column
	private EventRefDefinition eventRef;
	
	/**
	 * 
	 */
	@Column
	@JsonbTypeAdapter(value = SubFlowRefDefinitionAdapter.class)
	private Object subFlowRef;
	
	/**
	 * 
	 */
	@Column
	private String retryRef;
	
	/**
	 * 
	 */
	@Column
	private WorkflowError[] nonRetryableErrors;
	
	/**
	 * 
	 */
	@Column
	private WorkflowError[] retryableErrors;
	
	/**
	 * 
	 */
	@Column
	private ActionDataFilters actionDataFilter;
	
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

	public Object getFunctionRef() {
		return functionRef;
	}

	public void setFunctionRef(Object functionRef) {
		this.functionRef = functionRef;
	}

	public EventRefDefinition getEventRef() {
		return eventRef;
	}

	public void setEventRef(EventRefDefinition eventRef) {
		this.eventRef = eventRef;
	}

	public Object getSubFlowRef() {
		return subFlowRef;
	}

	public void setSubFlowRef(Object subFlowRef) {
		this.subFlowRef = subFlowRef;
	}

	public String getRetryRef() {
		return retryRef;
	}

	public void setRetryRef(String retryRef) {
		this.retryRef = retryRef;
	}

	public WorkflowError[] getNonRetryableErrors() {
		return nonRetryableErrors;
	}

	public void setNonRetryableErrors(WorkflowError[] nonRetryableErrors) {
		this.nonRetryableErrors = nonRetryableErrors;
	}

	public WorkflowError[] getRetryableErrors() {
		return retryableErrors;
	}

	public void setRetryableErrors(WorkflowError[] retryableErrors) {
		this.retryableErrors = retryableErrors;
	}

	public ActionDataFilters getActionDataFilter() {
		return actionDataFilter;
	}

	public void setActionDataFilter(ActionDataFilters actionDataFilter) {
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
