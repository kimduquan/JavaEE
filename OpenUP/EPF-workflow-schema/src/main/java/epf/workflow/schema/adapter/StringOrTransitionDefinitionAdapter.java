package epf.workflow.schema.adapter;

import epf.workflow.schema.TransitionDefinition;
import epf.workflow.schema.util.StringOrObjectJsonAdapter;

public class StringOrTransitionDefinitionAdapter extends StringOrObjectJsonAdapter<TransitionDefinition> {

	public StringOrTransitionDefinitionAdapter() {
		super(TransitionDefinition.class);
	}
}
