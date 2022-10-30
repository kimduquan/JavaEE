package epf.util.io;

import java.io.File;
import java.nio.file.Files;

/**
 * @author PC
 *
 */
public interface FileUtil {

	/**
	 * @param path
	 * @param encoding
	 * @return
	 * @throws Exception
	 */
	static String readString(final String path, final String encoding) throws Exception {
		final File file = new File(path);
		return new String(Files.readAllBytes(file.toPath()), encoding);
	}
	
	/**
	 * @param path
	 * @return
	 * @throws Exception
	 */
	static byte[] readAllBytes(final String path) throws Exception {
		final File file = new File(path);
		return Files.readAllBytes(file.toPath());
	}
}
