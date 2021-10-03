package epf.logging.bundle;

import java.util.logging.Logger;
import org.osgi.service.component.annotations.*;
import epf.logging.Logging;

@Component
public class LoggingImpl implements Logging {

	@Override
	public Logger getLogger(final String name) {
		return Logger.getLogger(name);
	}
}
