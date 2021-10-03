package epf.logging;

import java.util.logging.Logger;
import org.osgi.annotation.versioning.ProviderType;

/**
 * @author PC
 *
 */
@ProviderType
public interface LogManager {

	/**
	 * @param name
	 * @return
	 */
	Logger getLogger(final String name);
}
