package epf.webapp.workflow.flow;

import javax.faces.flow.FlowNode;

/**
 * @author PC
 *
 */
public class WorkflowNode extends FlowNode {
	
	/**
	 * 
	 */
	private String id;

	@Override
	public String getId() {
		return id;
	}

	public void setId(final String id) {
		this.id = id;
	}
}
