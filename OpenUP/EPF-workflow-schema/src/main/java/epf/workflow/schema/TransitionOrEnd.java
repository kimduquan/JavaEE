package epf.workflow.schema;

import epf.nosql.schema.BooleanOrObject;
import epf.nosql.schema.EitherJsonSerializer;
import epf.nosql.schema.EitherOrEither;
import epf.nosql.schema.StringOrObject;
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
