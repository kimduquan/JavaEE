package epf.webapp.workflow.flow;

import javax.faces.context.FacesContext;
import javax.faces.flow.ReturnNode;

/**
 * @author PC
 *
 */
public class WorkflowReturnNode extends ReturnNode {
	
	/**
	 * 
	 */
	private String id;

	@Override
	public String getFromOutcome(final FacesContext context) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getId() {
		return id;
	}

	public void setId(final String id) {
		this.id = id;
	}
}
