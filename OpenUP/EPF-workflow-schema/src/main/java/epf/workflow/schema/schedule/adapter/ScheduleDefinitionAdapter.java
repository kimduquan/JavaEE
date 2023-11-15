package epf.workflow.schema.schedule.adapter;

import epf.workflow.schema.schedule.ScheduleDefinition;
import epf.workflow.schema.util.StringOrObjectJsonAdapter;

/**
 * @author PC
 *
 */
public class ScheduleDefinitionAdapter extends StringOrObjectJsonAdapter<ScheduleDefinition> {

	/**
	 * 
	 */
	public ScheduleDefinitionAdapter() {
		super(ScheduleDefinition.class);
	}
}
