package epf.workflow.spi;

import epf.workflow.schema.Input;
import epf.workflow.schema.ValidationError;

public interface InputService {

	void validate(final Object rawInput, final Input input) throws ValidationError;
}
