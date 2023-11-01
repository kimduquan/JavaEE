package epf.workflow.schema.schedule.adapter;

import epf.util.json.ext.adapter.StringOrObjectAdapter;
import epf.workflow.schema.schedule.CronDefinition;

/**
 * @author PC
 *
 */
public class CronDefinitionAdapter extends StringOrObjectAdapter<CronDefinition> {

	/**
	 * 
	 */
	public CronDefinitionAdapter() {
		super(CronDefinition.class);
	}
}
