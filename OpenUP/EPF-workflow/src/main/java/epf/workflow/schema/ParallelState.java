package epf.workflow.schema;

import javax.validation.constraints.NotNull;
import jakarta.nosql.mapping.Column;
import jakarta.nosql.mapping.Embeddable;

/**
 * @author PC
 *
 */
@Embeddable
public class ParallelState extends State {

	/**
	 * 
	 */
	@NotNull
	@Column
	private ParallelStateBranch[] branches;
	/**
	 * 
	 */
	@Column
	private CompletionType completionType = CompletionType.allOf;
	/**
	 * 
	 */
	@Column
	private Object numCompleted;
	/**
	 * 
	 */
	@Column
	private Object timeouts;
	/**
	 * 
	 */
	@Column
	private StateDataFilters stateDataFilter;
	/**
	 * 
	 */
	@Column
	private ErrorDefinition[] onErrors;
	/**
	 * 
	 */
	@Column
	private Object transition;
	/**
	 * 
	 */
	@Column
	private String compensatedBy;
	/**
	 * 
	 */
	@Column
	private boolean usedForCompensation;
	/**
	 * 
	 */
	@Column
	private Object metadata;
	/**
	 * 
	 */
	@Column
	private Object end;
	
	public ParallelStateBranch[] getBranches() {
		return branches;
	}
	public void setBranches(ParallelStateBranch[] branches) {
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
	public StateDataFilters getStateDataFilter() {
		return stateDataFilter;
	}
	public void setStateDataFilter(StateDataFilters stateDataFilter) {
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
