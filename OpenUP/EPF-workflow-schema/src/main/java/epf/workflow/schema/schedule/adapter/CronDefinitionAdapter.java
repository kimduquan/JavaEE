package epf.workflow.schema.schedule.adapter;

import epf.workflow.schema.schedule.CronDefinition;
import epf.workflow.schema.util.EitherAdapter;

/**
 * @author PC
 *
 */
public class CronDefinitionAdapter extends EitherAdapter<String, CronDefinition> {

	/**
	 * 
	 */
	public CronDefinitionAdapter() {
		super(String.class ,CronDefinition.class);
	}
}
