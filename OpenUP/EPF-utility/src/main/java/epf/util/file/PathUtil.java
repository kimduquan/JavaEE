/**
 * 
 */
package epf.util.file;

import java.nio.file.FileSystems;
import java.nio.file.Path;

/**
 * @author PC
 *
 */
public interface PathUtil {

	/**
	 * @param first
	 * @param more
	 * @return
	 */
	static Path of(final String first, final String... more) {
        return FileSystems.getDefault().getPath(first, more);
    }
}
