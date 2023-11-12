package epf.workflow.schema;

import epf.workflow.schema.util.BooleanOrObject;
import epf.workflow.schema.util.EitherOrEither;
import epf.workflow.schema.util.StringOrObject;

/**
 * 
 */
public class TransitionOrEnd extends EitherOrEither<StringOrObject<TransitionDefinition>, BooleanOrObject<EndDefinition>> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	public static final String TRANSITION = "transition";
	
	/**
	 * 
	 */
	public static final String END = "end";
}
