package epf.workflow.schema.adapter;

import epf.workflow.schema.RetryDefinition;
import epf.workflow.schema.util.StringOrArrayJsonAdapter;

public class StringOrArrayRetryDefinitionAdapter extends StringOrArrayJsonAdapter<RetryDefinition> {

	public StringOrArrayRetryDefinitionAdapter() {
		super(RetryDefinition.class);
	}

}
