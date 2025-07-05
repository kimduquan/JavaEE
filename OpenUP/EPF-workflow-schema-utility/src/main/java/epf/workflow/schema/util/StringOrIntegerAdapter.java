package epf.workflow.schema.util;

/**
 * 
 */
public class StringOrIntegerAdapter extends EitherJsonAdapter<StringOrInteger, String, Integer> {

	/**
	 * 
	 */
	public StringOrIntegerAdapter() {
		super(StringOrInteger.class, String.class, Integer.class);
	}
}
