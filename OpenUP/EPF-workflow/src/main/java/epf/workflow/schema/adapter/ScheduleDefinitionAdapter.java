package epf.workflow.schema.adapter;

import epf.workflow.adapter.StringOrObjectAdapter;
import epf.workflow.schema.ScheduleDefinition;

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
