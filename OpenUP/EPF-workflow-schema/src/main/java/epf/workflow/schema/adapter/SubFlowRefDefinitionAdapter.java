package epf.workflow.schema.adapter;

import java.util.Arrays;
import epf.workflow.schema.SubFlowRefDefinition;
import epf.workflow.schema.util.StringOrObjectJsonAdapter;

/**
 * @author PC
 *
 */
public class SubFlowRefDefinitionAdapter extends StringOrObjectJsonAdapter<SubFlowRefDefinition> {

	/**
	 * 
	 */
	public SubFlowRefDefinitionAdapter() {
		super(SubFlowRefDefinition.class, Arrays.asList());
	}

}
