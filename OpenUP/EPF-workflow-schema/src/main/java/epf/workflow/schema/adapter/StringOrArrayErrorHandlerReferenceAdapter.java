package epf.workflow.schema.adapter;

import epf.workflow.schema.ErrorHandlerReference;
import epf.workflow.schema.util.StringOrArrayJsonAdapter;

public class StringOrArrayErrorHandlerReferenceAdapter extends StringOrArrayJsonAdapter<ErrorHandlerReference> {

	public StringOrArrayErrorHandlerReferenceAdapter() {
		super(ErrorHandlerReference.class);
	}

}
