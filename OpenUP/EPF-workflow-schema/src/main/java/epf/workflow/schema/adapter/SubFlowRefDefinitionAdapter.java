package epf.workflow.schema.adapter;

import java.util.Arrays;
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
		super(String.class, SubFlowRefDefinition.class, Arrays.asList());
	}

}
