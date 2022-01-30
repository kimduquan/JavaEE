package epf.file;

import java.nio.file.Path;
import org.osgi.annotation.versioning.ProviderType;

/**
 * @author PC
 *
 */
@ProviderType
public interface FileSystem {

	/**
	 * @param first
	 * @param more
	 * @return
	 */
	Path getPath(final String first, final String... more);
}
