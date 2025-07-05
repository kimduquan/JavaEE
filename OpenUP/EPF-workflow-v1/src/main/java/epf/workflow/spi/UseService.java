package epf.workflow.spi;

import java.util.Map;
import epf.workflow.schema.Use;
import epf.workflow.schema.Error;

public interface UseService {
	
	Map<String, Object> useSecrets(final Use use) throws Error;
}
