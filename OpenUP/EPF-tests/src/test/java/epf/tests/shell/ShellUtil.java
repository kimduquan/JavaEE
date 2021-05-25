/**
 * 
 */
package epf.tests.shell;

import java.io.IOException;
import java.nio.file.Path;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

import epf.tests.TestUtil;

/**
 * @author PC
 *
 */
public class ShellUtil {
	
	/**
	 * 
	 */
	private static Path shellPath;

	/**
	 * @return
	 */
	public static Path getShellPath() {
		if(shellPath == null) {
			shellPath = Path.of(System.getProperty("epf.shell.path"));
		}
		return shellPath;
	}
	
	/**
	 * @param builder
	 * @return
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public static Process waitFor(final ProcessBuilder builder) throws InterruptedException, IOException {
		final Process process = builder.start();
		TestUtil.waitUntil(o -> process.isAlive(), Duration.ofSeconds(10));
		process.waitFor(20, TimeUnit.SECONDS);
		return process;
	}
}
