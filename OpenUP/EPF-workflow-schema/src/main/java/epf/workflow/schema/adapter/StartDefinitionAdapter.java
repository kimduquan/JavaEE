package epf.workflow.schema.adapter;

import java.util.Arrays;
import epf.workflow.schema.StartDefinition;
import epf.workflow.schema.schedule.adapter.ScheduleDefinitionAdapter;
import epf.workflow.schema.util.StringOrObjectJsonAdapter;

/**
 * @author PC
 *
 */
public class StartDefinitionAdapter extends StringOrObjectJsonAdapter<StartDefinition> {

	/**
	 * 
	 */
	public StartDefinitionAdapter() {
		super(StartDefinition.class, Arrays.asList(ScheduleDefinitionAdapter.class));
	}
}
