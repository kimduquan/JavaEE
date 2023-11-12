package epf.workflow.schema.schedule.adapter;

import java.util.Arrays;
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
		super(ScheduleDefinition.class, Arrays.asList(CronDefinitionAdapter.class));
	}
}
