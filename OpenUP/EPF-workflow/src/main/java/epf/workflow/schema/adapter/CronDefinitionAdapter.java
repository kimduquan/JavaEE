package epf.workflow.schema.adapter;

import epf.workflow.schema.CronDefinition;

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
