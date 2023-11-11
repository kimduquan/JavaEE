package epf.workflow.schema.schedule.adapter;

import java.util.Arrays;
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
		super(String.class ,CronDefinition.class, Arrays.asList());
	}
}
