package epf.workflow.schema.error.adapter;

import epf.workflow.schema.error.ErrorThrowDefinition;
import epf.workflow.schema.util.BooleanOrObjectJsonAdapter;

public class BooleanOrErrorThrowDefinitionAdapter extends BooleanOrObjectJsonAdapter<ErrorThrowDefinition> {

	public BooleanOrErrorThrowDefinitionAdapter() {
		super(ErrorThrowDefinition.class);
	}

}
