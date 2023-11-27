package epf.workflow.schema.schedule.adapter;

import epf.workflow.schema.schedule.CronDefinition;
import epf.workflow.schema.util.StringOrObjectJsonAdapter;

/**
 * @author PC
 *
 */
public class StringOrCronDefinitionAdapter extends StringOrObjectJsonAdapter<CronDefinition> {

	/**
	 * 
	 */
	public StringOrCronDefinitionAdapter() {
		super(CronDefinition.class);
	}
}
