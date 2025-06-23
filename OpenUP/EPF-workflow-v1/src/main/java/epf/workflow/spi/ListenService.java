package epf.workflow.spi;

import epf.workflow.schema.Listen;

public interface ListenService {

	void listen(final Listen listen) throws Exception;
}
