package epf.workflow.schema.schedule.adapter;

import epf.workflow.schema.schedule.ScheduleDefinition;
import epf.workflow.schema.util.StringOrObjectJsonAdapter;

/**
 * @author PC
 *
 */
public class StringOrScheduleDefinitionAdapter extends StringOrObjectJsonAdapter<ScheduleDefinition> {

	/**
	 * 
	 */
	public StringOrScheduleDefinitionAdapter() {
		super(ScheduleDefinition.class);
	}
}
