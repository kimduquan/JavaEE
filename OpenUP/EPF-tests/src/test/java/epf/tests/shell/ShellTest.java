/**
 * 
 */
package epf.tests.shell;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

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
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		process.destroyForcibly();
	}

	@Test
	public void test() throws IOException {
		builder.command("powershell", "./epf.bat", "security", "login", "-u", "any_role1", "-p");
		process = builder.start();
		try(PrintWriter writer = new PrintWriter(process.getOutputStream())){
			writer.println("any_role");
		}
		try(InputStreamReader input = new InputStreamReader(process.getInputStream())){
			try(BufferedReader reader = new BufferedReader(input)){
				String token = reader.readLine();
				Assert.assertNotEquals("", token);
			}
		}
	}

}
