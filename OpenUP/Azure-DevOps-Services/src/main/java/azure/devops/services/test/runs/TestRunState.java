package azure.devops.services.test.runs;

/**
 * @author PC
 *
 */
public class TestRunState {

	/**
	 * Run is stopped and remaining tests have been aborted
	 */
	String aborted;
	/**
	 * All tests have completed or been skipped.
	 */
	String completed;
	/**
	 * Tests are running.
	 */
	String inProgress;
	/**
	 * Run requires investigation because of a test point failure 
	 * This is a legacy state and should not be used any more
	 */
	String needsInvestigation;
	/**
	 * The run is still being created. 
	 * No tests have started yet.
	 */
	String notStarted;
	/**
	 * Only used during an update to preserve the existing value.
	 */
	String unspecified;
	/**
	 * Run is currently initializing 
	 * This is a legacy state and should not be used any more
	 */
	String waiting;
	
	public String getAborted() {
		return aborted;
	}
	public void setAborted(String aborted) {
		this.aborted = aborted;
	}
	public String getCompleted() {
		return completed;
	}
	public void setCompleted(String completed) {
		this.completed = completed;
	}
	public String getInProgress() {
		return inProgress;
	}
	public void setInProgress(String inProgress) {
		this.inProgress = inProgress;
	}
	public String getNeedsInvestigation() {
		return needsInvestigation;
	}
	public void setNeedsInvestigation(String needsInvestigation) {
		this.needsInvestigation = needsInvestigation;
	}
	public String getNotStarted() {
		return notStarted;
	}
	public void setNotStarted(String notStarted) {
		this.notStarted = notStarted;
	}
	public String getUnspecified() {
		return unspecified;
	}
	public void setUnspecified(String unspecified) {
		this.unspecified = unspecified;
	}
	public String getWaiting() {
		return waiting;
	}
	public void setWaiting(String waiting) {
		this.waiting = waiting;
	}
}
