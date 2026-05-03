package epf.workflow.spi;

import epf.workflow.schema.Input;

public interface InputService {

	void validate(final Object rawInput, final Input input) throws Exception;
}
