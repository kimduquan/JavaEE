package epf.workflow.schema.auth.adapter;

import epf.workflow.schema.auth.AuthDefinition;
import epf.workflow.schema.util.StringOrArrayJsonAdapter;

/**
 * 
 */
public class StringOrArrayAuthDefinitionAdapter extends StringOrArrayJsonAdapter<AuthDefinition> {

	/**
	 * 
	 */
	public StringOrArrayAuthDefinitionAdapter() {
		super(AuthDefinition.class);
	}

}
