/**
 * 
 */
package epf.util.file;

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

	@Produces @ApplicationScoped
	public static FileSystem getFileSystem() {
		return FileSystems.getDefault();
	}
}
