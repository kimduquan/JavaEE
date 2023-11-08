package epf.workflow.schema.adapter;

import epf.workflow.schema.SubFlowRefDefinition;
import epf.workflow.schema.util.EitherAdapter;

/**
 * @author PC
 *
 */
public class SubFlowRefDefinitionAdapter extends EitherAdapter<String, SubFlowRefDefinition> {

	/**
	 * 
	 */
	public SubFlowRefDefinitionAdapter() {
		super(String.class, SubFlowRefDefinition.class);
	}

}
