package epf.workflow.spi;

import epf.workflow.schema.Retry;

public interface RetryService {

	void retry(final Retry retry) throws Exception;
}
