package epf.workflow.schema;

/**
 * @author PC
 *
 */
public class WorkflowExecTimeoutDefinition {

	/**
	 * 
	 */
	private String duration;
	/**
	 * 
	 */
	private boolean interrupt = false;
	/**
	 * 
	 */
	private String runBefore;
	
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public boolean isInterrupt() {
		return interrupt;
	}
	public void setInterrupt(boolean interrupt) {
		this.interrupt = interrupt;
	}
	public String getRunBefore() {
		return runBefore;
	}
	public void setRunBefore(String runBefore) {
		this.runBefore = runBefore;
	}
}
