package epf.workflow.schema.schedule.adapter;

import epf.workflow.schema.schedule.ScheduleDefinition;
import epf.workflow.schema.util.StringOrObjectJsonAdapter;

public class StringOrScheduleDefinitionAdapter extends StringOrObjectJsonAdapter<ScheduleDefinition> {

	public StringOrScheduleDefinitionAdapter() {
		super(ScheduleDefinition.class);
	}
}
