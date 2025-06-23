package epf.workflow.spi;

import epf.workflow.schema.ScriptProcess;

public interface ScriptProcessService {

	void run(final ScriptProcess scriptProcess) throws Exception;
}
