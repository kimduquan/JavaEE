package epf.workflow.schema.schedule.adapter;

import epf.workflow.schema.adapter.StringOrObjectAdapter;
import epf.workflow.schema.schedule.ScheduleDefinition;

/**
 * @author PC
 *
 */
public class ScheduleDefinitionAdapter extends StringOrObjectAdapter {

	/**
	 * 
	 */
	public ScheduleDefinitionAdapter() {
		super(ScheduleDefinition.class);
	}
}
