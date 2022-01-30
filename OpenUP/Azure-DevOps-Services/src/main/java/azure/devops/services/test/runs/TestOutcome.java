package azure.devops.services.test.runs;

public class TestOutcome {

	/**
	 * Test was aborted. 
	 * This was not caused by a user gesture, but rather by a framework decision.
	 */
	String aborted;
	/**
	 * Test had it chance for been executed but was not, as ITestElement.IsRunnable == false.
	 */
	String blocked;
	/**
	 * There was a system error while we were trying to execute a test.
	 */
	String error;
	/**
	 * Test was executed, but there were issues. 
	 * Issues may involve exceptions or failed assertions.
	 */
	String failed;
	/**
	 * Test is currently executing. 
	 * Added this for TCM charts
	 */
	String inProgress;
	/**
	 * Test has completed, but we can't say if it passed or failed. 
	 * May be used for aborted tests...
	 */
	String inconclusive;
	/**
	 * Test has not been completed, or the test type does not report pass/failure.
	 */
	String none;
	/**
	 * Test is Not Applicable for execution.
	 */
	String notApplicable;
	/**
	 * Test was not executed. 
	 * This was caused by a user gesture - e.g. user hit stop button.
	 */
	String notExecuted;
	/**
	 * Test is not impacted. 
	 * Added fot TIA.
	 */
	String notImpacted;
	/**
	 * Test was executed w/o any issues.
	 */
	String passed;
	/**
	 * Test is paused.
	 */
	String paused;
	/**
	 * The test timed out
	 */
	String timeout;
	/**
	 * Only used during an update to preserve the existing value.
	 */
	String unspecified;
	/**
	 * To be used by Run level results. 
	 * This is not a failure.
	 */
	String warning;
	
	public String getAborted() {
		return aborted;
	}
	public void setAborted(String aborted) {
		this.aborted = aborted;
	}
	public String getBlocked() {
		return blocked;
	}
	public void setBlocked(String blocked) {
		this.blocked = blocked;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public String getFailed() {
		return failed;
	}
	public void setFailed(String failed) {
		this.failed = failed;
	}
	public String getInProgress() {
		return inProgress;
	}
	public void setInProgress(String inProgress) {
		this.inProgress = inProgress;
	}
	public String getInconclusive() {
		return inconclusive;
	}
	public void setInconclusive(String inconclusive) {
		this.inconclusive = inconclusive;
	}
	public String getNone() {
		return none;
	}
	public void setNone(String none) {
		this.none = none;
	}
	public String getNotApplicable() {
		return notApplicable;
	}
	public void setNotApplicable(String notApplicable) {
		this.notApplicable = notApplicable;
	}
	public String getNotExecuted() {
		return notExecuted;
	}
	public void setNotExecuted(String notExecuted) {
		this.notExecuted = notExecuted;
	}
	public String getNotImpacted() {
		return notImpacted;
	}
	public void setNotImpacted(String notImpacted) {
		this.notImpacted = notImpacted;
	}
	public String getPassed() {
		return passed;
	}
	public void setPassed(String passed) {
		this.passed = passed;
	}
	public String getPaused() {
		return paused;
	}
	public void setPaused(String paused) {
		this.paused = paused;
	}
	public String getTimeout() {
		return timeout;
	}
	public void setTimeout(String timeout) {
		this.timeout = timeout;
	}
	public String getUnspecified() {
		return unspecified;
	}
	public void setUnspecified(String unspecified) {
		this.unspecified = unspecified;
	}
	public String getWarning() {
		return warning;
	}
	public void setWarning(String warning) {
		this.warning = warning;
	}
}
