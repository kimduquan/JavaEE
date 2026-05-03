package epf.workflow.spi;

import java.util.Map;
import epf.workflow.schema.Use;

public interface UseService {
	
	Map<String, Object> useSecrets(final Use use) throws Exception;
}
