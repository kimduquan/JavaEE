package epf.workflow.schema;

/**
 * @author PC
 *
 */
public class ActionDefinition {

	/**
	 * 
	 */
	private String name;
	
	/**
	 * 
	 */
	private Object functionRef;
	
	/**
	 * 
	 */
	private EventRefDefinition eventRef;
	
	/**
	 * 
	 */
	private Object subFlowRef;
	
	/**
	 * 
	 */
	private String retryRef;
	
	/**
	 * 
	 */
	private WorkflowError[] nonRetryableErrors;
	
	/**
	 * 
	 */
	private WorkflowError[] retryableErrors;
	
	/**
	 * 
	 */
	private ActionDataFilters actionDataFilter;
	
	/**
	 * 
	 */
	private Sleep sleep;
	
	/**
	 * 
	 */
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
