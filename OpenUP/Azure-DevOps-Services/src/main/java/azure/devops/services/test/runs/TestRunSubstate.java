package azure.devops.services.test.runs;

/**
 * @author PC
 * The types of sub states for test run.
 */
public class TestRunSubstate {

	/**
	 * Run state when it is Aborted By the System.
	 */
	String abortedBySystem;
	/**
	 * Run state after being Analysed.
	 */
	String analyzed;
	/**
	 * Run state while Creating Environment.
	 */
	String canceledByUser;
	/**
	 * Run state when cancellation is in Progress.
	 */
	String cancellationInProgress;
	/**
	 * Run state while Creating Environment.
	 */
	String creatingEnvironment;
	/**
	 * Run with noState.
	 */
	String none;
	/**
	 * Run state while Pending Analysis.
	 */
	String pendingAnalysis;
	/**
	 * Run state while Running Tests.
	 */
	String runningTests;
	/**
	 * Run state when run has timedOut.
	 */
	String timedOut;
	
	public String getAbortedBySystem() {
		return abortedBySystem;
	}
	public void setAbortedBySystem(String abortedBySystem) {
		this.abortedBySystem = abortedBySystem;
	}
	public String getAnalyzed() {
		return analyzed;
	}
	public void setAnalyzed(String analyzed) {
		this.analyzed = analyzed;
	}
	public String getCanceledByUser() {
		return canceledByUser;
	}
	public void setCanceledByUser(String canceledByUser) {
		this.canceledByUser = canceledByUser;
	}
	public String getCancellationInProgress() {
		return cancellationInProgress;
	}
	public void setCancellationInProgress(String cancellationInProgress) {
		this.cancellationInProgress = cancellationInProgress;
	}
	public String getCreatingEnvironment() {
		return creatingEnvironment;
	}
	public void setCreatingEnvironment(String creatingEnvironment) {
		this.creatingEnvironment = creatingEnvironment;
	}
	public String getNone() {
		return none;
	}
	public void setNone(String none) {
		this.none = none;
	}
	public String getPendingAnalysis() {
		return pendingAnalysis;
	}
	public void setPendingAnalysis(String pendingAnalysis) {
		this.pendingAnalysis = pendingAnalysis;
	}
	public String getRunningTests() {
		return runningTests;
	}
	public void setRunningTests(String runningTests) {
		this.runningTests = runningTests;
	}
	public String getTimedOut() {
		return timedOut;
	}
	public void setTimedOut(String timedOut) {
		this.timedOut = timedOut;
	}
}
