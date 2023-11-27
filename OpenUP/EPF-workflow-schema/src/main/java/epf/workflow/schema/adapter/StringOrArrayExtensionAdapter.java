package epf.workflow.schema.adapter;

import epf.workflow.schema.Extension;
import epf.workflow.schema.util.StringOrArrayJsonAdapter;

/**
 * 
 */
public class StringOrArrayExtensionAdapter extends StringOrArrayJsonAdapter<Extension> {

	/**
	 * 
	 */
	public StringOrArrayExtensionAdapter() {
		super(Extension.class);
	}

}
