package epf.workflow.event;

import java.net.URI;

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
public class EventAction extends Action {
	
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
	public EventAction(
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
		event.setSource(new URI(produceEventDefinition.getSource()));
		event.setType(produceEventDefinition.getType());
		event.setData(data);
		this.event.fire(event);
	}
}
