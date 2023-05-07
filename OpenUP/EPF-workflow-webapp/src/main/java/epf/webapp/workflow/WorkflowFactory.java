package epf.webapp.workflow;

import javax.faces.context.FacesContext;
import javax.faces.flow.FlowHandler;
import javax.faces.flow.FlowHandlerFactory;
import javax.faces.flow.FlowHandlerFactoryWrapper;

/**
 * @author PC
 *
 */
public class WorkflowFactory extends FlowHandlerFactoryWrapper {

	/**
	 * @param wrapped
	 */
	public WorkflowFactory(final FlowHandlerFactory wrapped) {
		super(wrapped);
	}
	
	@Override
	public FlowHandler createFlowHandler(final FacesContext context) {
		final FlowHandler handler = super.createFlowHandler(context);
		return handler;
	}
}