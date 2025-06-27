package epf.workflow.spi;

import epf.workflow.task.schema.Listen;

public interface ListenService {

	void listen(final Listen listen) throws Exception;
}
