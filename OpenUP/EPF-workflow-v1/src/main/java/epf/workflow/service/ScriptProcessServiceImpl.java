package epf.workflow.service;

import epf.workflow.schema.Duration;
import epf.workflow.schema.ProcessResult;
import epf.workflow.schema.ScriptProcess;
import epf.workflow.spi.ScriptProcessService;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ScriptProcessServiceImpl implements ScriptProcessService {

	@Override
	public ProcessResult run(final ScriptProcess scriptProcess, final boolean await, final Duration timeout) throws Error {
		return null;
	}

}
