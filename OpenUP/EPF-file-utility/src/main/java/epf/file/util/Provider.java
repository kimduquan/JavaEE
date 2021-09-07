/**
 * 
 */
package epf.file.util;

import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

/**
 * @author PC
 *
 */
@ApplicationScoped
public class Provider {

	/**
	 * @return
	 */
	@Produces @ApplicationScoped
	public static FileSystem getFileSystem() {
		return FileSystems.getDefault();
	}
}
