/**
 * 
 */
package epf.tests.shell;

import java.nio.file.Path;

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
}
