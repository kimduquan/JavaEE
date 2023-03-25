package epf.webapp.workflow.flow;

import java.util.Map;
import javax.faces.context.FacesContext;
import javax.faces.flow.FlowCallNode;
import javax.faces.flow.Parameter;

/**
 * @author PC
 *
 */
public class WorkflowFlowCallNode extends FlowCallNode {
	
	/**
	 * 
	 */
	private Map<String, Parameter> outboundParameters;
	
	/**
	 * 
	 */
	private String id;

	@Override
	public Map<String, Parameter> getOutboundParameters() {
		return outboundParameters;
	}

	@Override
	public String getCalledFlowDocumentId(final FacesContext context) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCalledFlowId(final FacesContext context) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getId() {
		return id;
	}

	public void setOutboundParameters(final Map<String, Parameter> outboundParameters) {
		this.outboundParameters = outboundParameters;
	}

	public void setId(final String id) {
		this.id = id;
	}
}
