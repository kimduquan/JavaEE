package epf.workflow.schema.schedule.adapter;

import java.util.Arrays;
import epf.workflow.schema.schedule.ScheduleDefinition;
import epf.workflow.schema.util.EitherJsonAdapter;

/**
 * @author PC
 *
 */
public class ScheduleDefinitionAdapter extends EitherJsonAdapter<String, ScheduleDefinition> {

	/**
	 * 
	 */
	public ScheduleDefinitionAdapter() {
		super(String.class, ScheduleDefinition.class, Arrays.asList(CronDefinitionAdapter.class));
	}
}
