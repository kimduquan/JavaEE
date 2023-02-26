package epf.workflow.schedule.schema.adapter;

import epf.workflow.schedule.schema.CronDefinition;
import epf.workflow.schema.adapter.StringOrObjectAdapter;

/**
 * @author PC
 *
 */
public class CronDefinitionAdapter extends StringOrObjectAdapter {

	/**
	 * 
	 */
	public CronDefinitionAdapter() {
		super(CronDefinition.class);
	}
}
