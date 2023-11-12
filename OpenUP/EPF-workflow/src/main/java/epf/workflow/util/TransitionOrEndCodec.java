package epf.workflow.util;

import org.bson.codecs.configuration.CodecRegistry;
import epf.workflow.schema.EndDefinition;
import epf.workflow.schema.TransitionDefinition;
import epf.workflow.schema.TransitionOrEnd;
import epf.workflow.schema.util.BooleanOrObject;
import epf.workflow.schema.util.StringOrObject;

/**
 * 
 */
public class TransitionOrEndCodec extends EitherOrEitherCodec<StringOrObject<TransitionDefinition>, BooleanOrObject<EndDefinition>> {

	public TransitionOrEndCodec(final CodecRegistry registry) {
		super(registry, TransitionOrEnd.TRANSITION, new StringOrObjectCodec<TransitionDefinition>(registry), TransitionOrEnd.END, new BooleanOrObjectCodec<EndDefinition>(registry), TransitionOrEnd.class);
	}
}
