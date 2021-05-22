/**
 * 
 */
package epf.tests.shell;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Scanner;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import epf.tests.TestUtil;

/**
 * @author PC
 *
 */
public class ShellTest {
	
	private static File workingDir;
	private ProcessBuilder builder;
	private Process process;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		workingDir = ShellUtil.getShellPath().toAbsolutePath().toRealPath().toFile();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		builder = new ProcessBuilder();
		builder.directory(workingDir);
		builder.inheritIO();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		process.destroyForcibly();
	}

	@Test
	public void test() throws IOException, InterruptedException {
		builder.command("powershell", "./epf", "security", "login", "-u", "any_role1", "-p");
		process = builder.start();
		TestUtil.waitUntil(o -> process.isAlive(), Duration.ofSeconds(10));
		try(Scanner scanner = new Scanner(process.getInputStream())){
			while(scanner.hasNextLine()) {
				String line = scanner.nextLine();
				if(line.startsWith("Enter")) {
					break;
				}
			}
		}
	}

}
