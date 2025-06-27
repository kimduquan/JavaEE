package epf.workflow.spi;

import epf.workflow.task.schema.Fork;

public interface ForkService {

	void fork(final Fork fork) throws Exception;
}
