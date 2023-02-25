package epf.workflow.schema.adapter;

import epf.workflow.adapter.StringOrObjectAdapter;
import epf.workflow.schema.SubFlowRefDefinition;

/**
 * @author PC
 *
 */
public class SubFlowRefDefinitionAdapter extends StringOrObjectAdapter {

	/**
	 * 
	 */
	public SubFlowRefDefinitionAdapter() {
		super(SubFlowRefDefinition.class);
	}

}
