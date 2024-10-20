package epf.workflow.schema.adapter;

import epf.workflow.schema.RetryDefinition;
import epf.workflow.schema.util.StringOrObjectJsonAdapter;

public class StringOrRetryDefinitionAdapter extends StringOrObjectJsonAdapter<RetryDefinition> {

	public StringOrRetryDefinitionAdapter() {
		super(RetryDefinition.class);
	}
}
