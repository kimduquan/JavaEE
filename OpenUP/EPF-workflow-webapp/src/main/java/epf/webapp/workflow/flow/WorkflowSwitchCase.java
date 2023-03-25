package epf.webapp.workflow.flow;

import javax.faces.context.FacesContext;
import javax.faces.flow.SwitchCase;

/**
 * @author PC
 *
 */
public class WorkflowSwitchCase extends SwitchCase {
	
	/**
	 * 
	 */
	private String fromOutcome;

	@Override
	public String getFromOutcome() {
		return fromOutcome;
	}

	@Override
	public Boolean getCondition(final FacesContext context) {
		// TODO Auto-generated method stub
		return null;
	}

	public void setFromOutcome(final String fromOutcome) {
		this.fromOutcome = fromOutcome;
	}

}
