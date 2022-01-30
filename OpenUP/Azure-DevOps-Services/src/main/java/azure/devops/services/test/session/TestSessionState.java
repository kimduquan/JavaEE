package azure.devops.services.test.session;

/**
 * @author PC
 * State of the test session
 */
public class TestSessionState {

	/**
	 * The session has completed.
	 */
	String completed;
	/**
	 * This is required for Feedback session which are declined
	 */
	String declined;
	/**
	 * The session is running.
	 */
	String inProgress;
	/**
	 * The session is still being created.
	 */
	String notStarted;
	/**
	 * The session has paused.
	 */
	String paused;
	/**
	 * Only used during an update to preserve the existing value.
	 */
	String unspecified;
	
	public String getCompleted() {
		return completed;
	}
	public void setCompleted(String completed) {
		this.completed = completed;
	}
	public String getDeclined() {
		return declined;
	}
	public void setDeclined(String declined) {
		this.declined = declined;
	}
	public String getInProgress() {
		return inProgress;
	}
	public void setInProgress(String inProgress) {
		this.inProgress = inProgress;
	}
	public String getNotStarted() {
		return notStarted;
	}
	public void setNotStarted(String notStarted) {
		this.notStarted = notStarted;
	}
	public String getPaused() {
		return paused;
	}
	public void setPaused(String paused) {
		this.paused = paused;
	}
	public String getUnspecified() {
		return unspecified;
	}
	public void setUnspecified(String unspecified) {
		this.unspecified = unspecified;
	}
}
