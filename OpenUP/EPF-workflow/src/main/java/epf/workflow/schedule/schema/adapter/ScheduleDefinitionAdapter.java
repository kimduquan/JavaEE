package epf.workflow.schedule.schema.adapter;

import epf.workflow.schedule.schema.ScheduleDefinition;
import epf.workflow.schema.adapter.StringOrObjectAdapter;

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
