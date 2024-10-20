package epf.workflow.schema.adapter;

import epf.workflow.schema.ContinueAs;
import epf.workflow.schema.util.StringOrObjectJsonAdapter;

public class StringOrContinueAsAdapter extends StringOrObjectJsonAdapter<ContinueAs> {

	public StringOrContinueAsAdapter() {
		super(ContinueAs.class);
	}
}
