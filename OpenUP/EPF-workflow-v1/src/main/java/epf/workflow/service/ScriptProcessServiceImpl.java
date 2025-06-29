package epf.workflow.service;

import epf.workflow.schema.ProcessResult;
import epf.workflow.schema.ScriptProcess;
import epf.workflow.spi.ScriptProcessService;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ScriptProcessServiceImpl implements ScriptProcessService {

	@Override
	public ProcessResult run(final ScriptProcess scriptProcess, final boolean await) throws Error {
		return null;
	}

}
