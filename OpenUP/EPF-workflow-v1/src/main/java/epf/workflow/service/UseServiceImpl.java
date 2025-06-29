package epf.workflow.service;

import java.util.Map;
import epf.workflow.schema.Error;
import epf.workflow.schema.Use;
import epf.workflow.spi.UseService;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UseServiceImpl implements UseService {

	@Override
	public Map<String, Object> useSecrets(final Use use) throws Error {
		return Map.of();
	}

}
