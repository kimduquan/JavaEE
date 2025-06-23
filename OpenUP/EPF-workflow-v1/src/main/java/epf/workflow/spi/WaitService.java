package epf.workflow.spi;

import epf.workflow.schema.Wait;

public interface WaitService {

	void wait(final Wait wait) throws Exception;
}
