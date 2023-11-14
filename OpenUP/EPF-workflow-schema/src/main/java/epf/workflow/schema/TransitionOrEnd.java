package epf.workflow.schema;

import epf.workflow.schema.util.BooleanOrObject;
import epf.workflow.schema.util.EitherJsonSerializer;
import epf.workflow.schema.util.EitherOrEither;
import epf.workflow.schema.util.StringOrObject;
import jakarta.json.bind.annotation.JsonbTypeSerializer;

/**
 * 
 */
@JsonbTypeSerializer(value = EitherJsonSerializer.class)
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
