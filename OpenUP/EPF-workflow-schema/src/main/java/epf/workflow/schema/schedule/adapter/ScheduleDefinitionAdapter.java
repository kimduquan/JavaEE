package epf.workflow.schema.schedule.adapter;

import epf.workflow.schema.schedule.ScheduleDefinition;
import epf.workflow.schema.util.EitherAdapter;

/**
 * @author PC
 *
 */
public class ScheduleDefinitionAdapter extends EitherAdapter<String, ScheduleDefinition> {

	/**
	 * 
	 */
	public ScheduleDefinitionAdapter() {
		super(String.class, ScheduleDefinition.class);
	}
}
