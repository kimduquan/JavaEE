package epf.workflow.schema.util;

/**
 * 
 */
public class StringOrNumberAdapter extends EitherJsonAdapter<StringOrNumber, String, Number> {

	/**
	 * 
	 */
	public StringOrNumberAdapter() {
		super(StringOrNumber.class, String.class, Number.class);
	}
}
