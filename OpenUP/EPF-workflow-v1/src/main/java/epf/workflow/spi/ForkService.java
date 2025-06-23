package epf.workflow.spi;

import epf.workflow.schema.Fork;

public interface ForkService {

	void fork(final Fork fork) throws Exception;
}
