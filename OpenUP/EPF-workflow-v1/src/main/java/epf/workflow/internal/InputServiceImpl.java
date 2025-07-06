package epf.workflow.internal;

import epf.workflow.schema.Input;
import epf.workflow.schema.ValidationError;
import epf.workflow.spi.InputService;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class InputServiceImpl implements InputService {

	@Override
	public void validate(final Object rawInput, final Input input) throws ValidationError {
		
	}
}
