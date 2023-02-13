package epf.workflow.schema;

/**
 * @author PC
 *
 */
public class ForEachState extends State {

	/**
	 * 
	 */
	private String inputCollection;
	/**
	 * 
	 */
	private String outputCollection;
	/**
	 * 
	 */
	private String iterationParam;
	/**
	 * 
	 */
	private Object batchSize;
	/**
	 * 
	 */
	private Mode mode = Mode.parallel;
	/**
	 * 
	 */
	private Object[] actions;
	/**
	 * 
	 */
	private Object timeouts;
	/**
	 * 
	 */
	private Object stateDataFilter;
	/**
	 * 
	 */
	private ErrorDefinition[] onErrors;
	/**
	 * 
	 */
	private Object transition;
	/**
	 * 
	 */
	private String compensatedBy;
	/**
	 * 
	 */
	private boolean usedForCompensation	= false;
	/**
	 * 
	 */
	private Object metadata;
	/**
	 * 
	 */
	private Object end;
	
	public String getInputCollection() {
		return inputCollection;
	}
	public void setInputCollection(String inputCollection) {
		this.inputCollection = inputCollection;
	}
	public String getOutputCollection() {
		return outputCollection;
	}
	public void setOutputCollection(String outputCollection) {
		this.outputCollection = outputCollection;
	}
	public String getIterationParam() {
		return iterationParam;
	}
	public void setIterationParam(String iterationParam) {
		this.iterationParam = iterationParam;
	}
	public Object getBatchSize() {
		return batchSize;
	}
	public void setBatchSize(Object batchSize) {
		this.batchSize = batchSize;
	}
	public Mode getMode() {
		return mode;
	}
	public void setMode(Mode mode) {
		this.mode = mode;
	}
	public Object[] getActions() {
		return actions;
	}
	public void setActions(Object[] actions) {
		this.actions = actions;
	}
	public Object getTimeouts() {
		return timeouts;
	}
	public void setTimeouts(Object timeouts) {
		this.timeouts = timeouts;
	}
	public Object getStateDataFilter() {
		return stateDataFilter;
	}
	public void setStateDataFilter(Object stateDataFilter) {
		this.stateDataFilter = stateDataFilter;
	}
	public ErrorDefinition[] getOnErrors() {
		return onErrors;
	}
	public void setOnErrors(ErrorDefinition[] onErrors) {
		this.onErrors = onErrors;
	}
	public Object getTransition() {
		return transition;
	}
	public void setTransition(Object transition) {
		this.transition = transition;
	}
	public String getCompensatedBy() {
		return compensatedBy;
	}
	public void setCompensatedBy(String compensatedBy) {
		this.compensatedBy = compensatedBy;
	}
	public boolean isUsedForCompensation() {
		return usedForCompensation;
	}
	public void setUsedForCompensation(boolean usedForCompensation) {
		this.usedForCompensation = usedForCompensation;
	}
	public Object getMetadata() {
		return metadata;
	}
	public void setMetadata(Object metadata) {
		this.metadata = metadata;
	}
	public Object getEnd() {
		return end;
	}
	public void setEnd(Object end) {
		this.end = end;
	}
}
