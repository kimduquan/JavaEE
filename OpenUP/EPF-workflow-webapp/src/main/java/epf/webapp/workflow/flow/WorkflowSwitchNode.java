package epf.webapp.workflow.flow;

import java.util.List;
import javax.faces.context.FacesContext;
import javax.faces.flow.SwitchCase;
import javax.faces.flow.SwitchNode;

/**
 * @author PC
 *
 */
public class WorkflowSwitchNode extends SwitchNode {
	
	/**
	 * 
	 */
	private List<SwitchCase> cases;
	
	/**
	 * 
	 */
	private String id;

	@Override
	public List<SwitchCase> getCases() {
		return cases;
	}

	@Override
	public String getDefaultOutcome(final FacesContext context) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getId() {
		return id;
	}

	public void setCases(final List<SwitchCase> cases) {
		this.cases = cases;
	}

	public void setId(final String id) {
		this.id = id;
	}
}
