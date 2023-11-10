package epf.workflow.schema.adapter;

import epf.workflow.schema.SubFlowRefDefinition;
import epf.workflow.schema.util.EitherJsonAdapter;

/**
 * @author PC
 *
 */
public class SubFlowRefDefinitionAdapter extends EitherJsonAdapter<String, SubFlowRefDefinition> {

	/**
	 * 
	 */
	public SubFlowRefDefinitionAdapter() {
		super(String.class, SubFlowRefDefinition.class);
	}

}
