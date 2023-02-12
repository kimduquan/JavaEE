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
	private Object eventRef;
	
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
	private Object[] nonRetryableErrors;
	
	/**
	 * 
	 */
	private Object[] retryableErrors;
	
	/**
	 * 
	 */
	private Object actionDataFilter;
	
	/**
	 * 
	 */
	private Object sleep;
	
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

	public Object getEventRef() {
		return eventRef;
	}

	public void setEventRef(Object eventRef) {
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

	public Object[] getNonRetryableErrors() {
		return nonRetryableErrors;
	}

	public void setNonRetryableErrors(Object[] nonRetryableErrors) {
		this.nonRetryableErrors = nonRetryableErrors;
	}

	public Object[] getRetryableErrors() {
		return retryableErrors;
	}

	public void setRetryableErrors(Object[] retryableErrors) {
		this.retryableErrors = retryableErrors;
	}

	public Object getActionDataFilter() {
		return actionDataFilter;
	}

	public void setActionDataFilter(Object actionDataFilter) {
		this.actionDataFilter = actionDataFilter;
	}

	public Object getSleep() {
		return sleep;
	}

	public void setSleep(Object sleep) {
		this.sleep = sleep;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}
}
