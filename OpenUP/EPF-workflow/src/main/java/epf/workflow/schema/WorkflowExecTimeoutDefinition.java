package epf.workflow.schema;

import jakarta.nosql.mapping.Column;
import jakarta.nosql.mapping.Embeddable;

/**
 * @author PC
 *
 */
@Embeddable
public class WorkflowExecTimeoutDefinition {

	/**
	 * 
	 */
	@Column
	private String duration;
	/**
	 * 
	 */
	@Column
	private boolean interrupt = false;
	/**
	 * 
	 */
	@Column
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
