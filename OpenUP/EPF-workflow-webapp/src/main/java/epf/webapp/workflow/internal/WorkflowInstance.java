package epf.webapp.workflow.internal;

import java.io.Serializable;
import javax.faces.flow.FlowScoped;

/**
 * @author PC
 *
 */
@FlowScoped(value = epf.naming.Naming.WORKFLOW)
public class WorkflowInstance implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	private String workflow;

	public String getWorkflow() {
		return workflow;
	}

	public void setWorkflow(final String workflow) {
		this.workflow = workflow;
	}

	/**
	 * @return
	 */
	public String start() {
		return "start";
	}
}
