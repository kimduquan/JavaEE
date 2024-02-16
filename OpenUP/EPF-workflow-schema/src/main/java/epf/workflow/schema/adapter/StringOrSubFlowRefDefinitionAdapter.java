package epf.workflow.schema.adapter;

import epf.workflow.schema.SubFlowRefDefinition;
import epf.workflow.schema.util.StringOrObjectJsonAdapter;

/**
 * @author PC
 *
 */
public class StringOrSubFlowRefDefinitionAdapter extends StringOrObjectJsonAdapter<SubFlowRefDefinition> {

	/**
	 * 
	 */
	public StringOrSubFlowRefDefinitionAdapter() {
		super(SubFlowRefDefinition.class);
	}

}