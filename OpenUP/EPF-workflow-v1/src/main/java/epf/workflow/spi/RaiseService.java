package epf.workflow.spi;

import epf.workflow.schema.Raise;

public interface RaiseService {

	void raise(final Raise raise) throws Exception;
}
