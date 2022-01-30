package epf.messaging;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author PC
 *
 */
@ProviderType
public interface Messaging {

	/**
	 * @return
	 */
	Context createContext();
}
