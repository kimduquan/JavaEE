package epf.workflow.schema.schedule.adapter;

import epf.util.json.ext.adapter.StringOrObjectAdapter;
import epf.workflow.schema.schedule.ScheduleDefinition;

/**
 * @author PC
 *
 */
public class ScheduleDefinitionAdapter extends StringOrObjectAdapter<ScheduleDefinition> {

	/**
	 * 
	 */
	public ScheduleDefinitionAdapter() {
		super(ScheduleDefinition.class);
	}
}
