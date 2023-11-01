package epf.workflow.schema.adapter;

import epf.util.json.ext.adapter.StringOrObjectAdapter;
import epf.workflow.schema.SubFlowRefDefinition;

/**
 * @author PC
 *
 */
public class SubFlowRefDefinitionAdapter extends StringOrObjectAdapter<SubFlowRefDefinition> {

	/**
	 * 
	 */
	public SubFlowRefDefinitionAdapter() {
		super(SubFlowRefDefinition.class);
	}

}
