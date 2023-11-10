package epf.workflow.schema.schedule.adapter;

import epf.workflow.schema.schedule.CronDefinition;
import epf.workflow.schema.util.EitherJsonAdapter;

/**
 * @author PC
 *
 */
public class CronDefinitionAdapter extends EitherJsonAdapter<String, CronDefinition> {

	/**
	 * 
	 */
	public CronDefinitionAdapter() {
		super(String.class ,CronDefinition.class);
	}
}
