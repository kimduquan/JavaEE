package epf.workflow.spi;

import epf.workflow.schema.ShellProcess;

public interface ShellProcessService {

	void run(final ShellProcess shellProcess) throws Exception;
}
