package epf.workflow.event.util;

import java.util.Arrays;

import epf.workflow.event.schema.EventDefinition;
import epf.workflow.schema.WorkflowDefinition;

/**
 * @author PC
 *
 */
public interface EventUtil {
	
	/**
	 * @param workflowDefinition
	 * @param eventRef
	 * @return
	 */
	static EventDefinition getEventDefinition(final WorkflowDefinition workflowDefinition, final String eventRef) {
		if(workflowDefinition.getEvents() instanceof EventDefinition[]) {
			return Arrays.asList((EventDefinition[])workflowDefinition.getEvents()).stream().filter(eventDef -> eventDef.getName().equals(eventRef)).findFirst().get();
		}
		return null;
	}
}
