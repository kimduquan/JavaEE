package epf.workflow.event;

import epf.workflow.WorkflowData;
import epf.workflow.action.Action;
import epf.workflow.action.schema.ActionDefinition;
import epf.workflow.event.schema.EventDefinition;
import epf.workflow.event.util.EventUtil;
import epf.workflow.schema.WorkflowDefinition;
import epf.workflow.util.ELUtil;

/**
 * @author PC
 *
 */
public class ProduceEventAction extends Action {
	
	/**
	 * 
	 */
	private final javax.enterprise.event.Event<Event> event;

	/**
	 * @param workflowDefinition
	 * @param actionDefinition
	 * @param event
	 * @param workflowData
	 */
	public ProduceEventAction(
			WorkflowDefinition workflowDefinition, 
			ActionDefinition actionDefinition, 
			javax.enterprise.event.Event<Event> event,
			WorkflowData workflowData) {
		super(workflowDefinition, actionDefinition, workflowData);
		this.event = event;
	}

	@Override
	protected void perform() throws Exception {
		final EventDefinition produceEventDefinition = EventUtil.getEventDefinition(getWorkflowDefinition(), getActionDefinition().getEventRef().getProduceEventRef());
		Object data = null;
		if(getActionDefinition().getEventRef().getData() != null) {
			if(getActionDefinition().getEventRef().getData() instanceof String) {
				data = ELUtil.getValue((String)getActionDefinition().getEventRef().getData(), getWorkflowData().getOutput());
			}
			else {
				data = getWorkflowData().getOutput();
			}
		}
		final Event event = new Event();
		event.setSource(produceEventDefinition.getSource());
		event.setType(produceEventDefinition.getType());
		event.setData(data);
		this.event.fire(event);
	}
}
