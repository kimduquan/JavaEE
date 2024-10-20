package epf.workflow.schema.adapter;

import epf.workflow.schema.error.ErrorHandlingConfiguration;
import epf.workflow.schema.util.StringOrObjectJsonAdapter;

/**
 * 
 */
public class StringOrErrorHandlingConfigurationAdapter extends StringOrObjectJsonAdapter<ErrorHandlingConfiguration> {

	public StringOrErrorHandlingConfigurationAdapter() {
		super(ErrorHandlingConfiguration.class);
	}
}
