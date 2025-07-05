package epf.workflow.schema.function.adapter;

import epf.workflow.schema.function.FunctionDefinition;
import epf.workflow.schema.util.StringOrArrayJsonAdapter;

public class StringOrArrayFunctionDefinitionAdapter extends StringOrArrayJsonAdapter<FunctionDefinition> {

	public StringOrArrayFunctionDefinitionAdapter() {
		super(FunctionDefinition.class);
	}

}
