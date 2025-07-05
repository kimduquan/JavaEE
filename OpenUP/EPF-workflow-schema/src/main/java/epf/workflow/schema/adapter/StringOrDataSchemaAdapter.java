package epf.workflow.schema.adapter;

import epf.workflow.schema.DataSchema;
import epf.workflow.schema.util.StringOrObjectJsonAdapter;

public class StringOrDataSchemaAdapter extends StringOrObjectJsonAdapter<DataSchema> {

	public StringOrDataSchemaAdapter() {
		super(DataSchema.class);
	}

}
