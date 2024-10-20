package epf.workflow.schema.util;

/**
 * 
 */
public class StringOrFloatAdapter extends EitherJsonAdapter<StringOrFloat, String, Float> {

	/**
	 * 
	 */
	public StringOrFloatAdapter() {
		super(StringOrFloat.class, String.class, Float.class);
	}
}
