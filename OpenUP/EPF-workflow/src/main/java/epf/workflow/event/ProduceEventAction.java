package epf.workflow.event;

import epf.workflow.action.Action;
import epf.workflow.schema.WorkflowData;
import epf.workflow.schema.WorkflowDefinition;
import epf.workflow.schema.action.ActionDefinition;
import epf.workflow.schema.event.EventDefinition;
import epf.workflow.util.ELUtil;

/**
 * @author PC
 *
 */
public class ProduceEventAction extends Action {
	
	/**
	 * 
	 */
	private final EventDefinition eventDefinition;
	
	/**
	 * 
	 */
	private final jakarta.enterprise.event.Event<Event> event;

	/**
	 * @param workflowDefinition
	 * @param actionDefinition
	 * @param eventDefinition
	 * @param event
	 * @param workflowData
	 */
	public ProduceEventAction(
			WorkflowDefinition workflowDefinition, 
			ActionDefinition actionDefinition, 
			EventDefinition eventDefinition,
			jakarta.enterprise.event.Event<Event> event,
			WorkflowData workflowData) {
		super(workflowDefinition, actionDefinition, workflowData);
		this.eventDefinition = eventDefinition;
		this.event = event;
	}

	@Override
	protected void perform() throws Exception {
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
		event.setSource(eventDefinition.getSource());
		event.setType(eventDefinition.getType());
		event.setData(data);
		this.event.fire(event);
	}
}
