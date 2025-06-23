package epf.workflow.spi;

import epf.workflow.schema.Run;

public interface RunService {

	void run(final Run run) throws Exception;
}
