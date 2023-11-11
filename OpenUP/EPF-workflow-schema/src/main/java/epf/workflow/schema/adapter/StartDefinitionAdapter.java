package epf.workflow.schema.adapter;

import java.util.Arrays;
import epf.workflow.schema.StartDefinition;
import epf.workflow.schema.schedule.adapter.ScheduleDefinitionAdapter;
import epf.workflow.schema.util.EitherJsonAdapter;

/**
 * @author PC
 *
 */
public class StartDefinitionAdapter extends EitherJsonAdapter<String, StartDefinition> {

	/**
	 * 
	 */
	public StartDefinitionAdapter() {
		super(String.class, StartDefinition.class, Arrays.asList(ScheduleDefinitionAdapter.class));
	}
}
