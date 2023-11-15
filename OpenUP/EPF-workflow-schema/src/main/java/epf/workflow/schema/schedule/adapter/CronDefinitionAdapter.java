package epf.workflow.schema.schedule.adapter;

import epf.workflow.schema.schedule.CronDefinition;
import epf.workflow.schema.util.StringOrObjectJsonAdapter;

/**
 * @author PC
 *
 */
public class CronDefinitionAdapter extends StringOrObjectJsonAdapter<CronDefinition> {

	/**
	 * 
	 */
	public CronDefinitionAdapter() {
		super(CronDefinition.class);
	}
}
