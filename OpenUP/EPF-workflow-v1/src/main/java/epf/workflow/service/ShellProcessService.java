package epf.workflow.service;

import epf.workflow.schema.ShellProcess;

public interface ShellProcessService {

	void run(final ShellProcess shellProcess) throws Exception;
}
