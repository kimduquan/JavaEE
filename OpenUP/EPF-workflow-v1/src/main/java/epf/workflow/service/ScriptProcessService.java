package epf.workflow.service;

import epf.workflow.schema.ScriptProcess;

public interface ScriptProcessService {

	void run(final ScriptProcess scriptProcess) throws Exception;
}
