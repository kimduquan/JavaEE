/**
 * 
 */
package epf.tests.shell;

import java.nio.file.Path;
import java.util.Scanner;

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
	 * @param process
	 * @throws InterruptedException
	 */
	public static void print(Process process) throws InterruptedException {
		new Thread(() -> {
			try(Scanner scanner = new Scanner(process.getInputStream())){
				while(scanner.hasNextLine()) {
					String line = scanner.nextLine();
					System.out.println(line);
				}
			}
		})
		.start();
	}
}
