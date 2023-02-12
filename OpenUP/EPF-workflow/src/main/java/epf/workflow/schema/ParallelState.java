package epf.workflow.schema;

/**
 * @author PC
 *
 */
public class ParallelState extends State {

	/**
	 * 
	 */
	private Object[] branches;
	/**
	 * 
	 */
	private CompletionType completionType = CompletionType.allOf;
	/**
	 * 
	 */
	private Object numCompleted;
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
	private Object[] onErrors;
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
	private boolean usedForCompensation;
	/**
	 * 
	 */
	private Object metadata;
	/**
	 * 
	 */
	private Object end;
	
	public Object[] getBranches() {
		return branches;
	}
	public void setBranches(Object[] branches) {
		this.branches = branches;
	}
	public CompletionType getCompletionType() {
		return completionType;
	}
	public void setCompletionType(CompletionType completionType) {
		this.completionType = completionType;
	}
	public Object getNumCompleted() {
		return numCompleted;
	}
	public void setNumCompleted(Object numCompleted) {
		this.numCompleted = numCompleted;
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
	public Object[] getOnErrors() {
		return onErrors;
	}
	public void setOnErrors(Object[] onErrors) {
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
