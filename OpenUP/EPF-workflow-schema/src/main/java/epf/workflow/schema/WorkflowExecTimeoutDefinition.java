package epf.workflow.schema;

import jakarta.nosql.Column;
import java.io.Serializable;
import org.eclipse.jnosql.mapping.Embeddable;

/**
 * @author PC
 *
 */
@Embeddable
public class WorkflowExecTimeoutDefinition implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	@Column
	private String duration;
	/**
	 * 
	 */
	@Column
	private Boolean interrupt = false;
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
	public Boolean isInterrupt() {
		return interrupt;
	}
	public void setInterrupt(Boolean interrupt) {
		this.interrupt = interrupt;
	}
	public String getRunBefore() {
		return runBefore;
	}
	public void setRunBefore(String runBefore) {
		this.runBefore = runBefore;
	}
}
