/**
 * 
 */
package epf.tests.shell;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import epf.client.gateway.Gateway;
import epf.tests.TestUtil;

/**
 * @author PC
 *
 */
public class ShellTest {
	
	private static File workingDir;
	private ProcessBuilder builder;
	private Process process;
	Path out;
	Path in;
	Path err;
	Path tempDir;

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
		tempDir = Files.createTempDirectory("temp");
		in = Files.createTempFile(tempDir, "in", "in");
		out = Files.createTempFile(tempDir, "out", "out");
		err = Files.createTempFile(tempDir, "err", "err");
		builder = new ProcessBuilder();
		builder.directory(workingDir);
		builder.environment().put(Gateway.GATEWAY_URL, System.getProperty(Gateway.GATEWAY_URL));
		builder.redirectError(err.toFile());
		builder.redirectInput(in.toFile());
		builder.redirectOutput(out.toFile());
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		process.destroyForcibly();
		in.toFile().delete();
		out.toFile().delete();
		err.toFile().delete();
		tempDir.toFile().delete();
	}

	@Test
	public void testSecurity_Login() throws IOException, InterruptedException {
		builder.command("powershell", "./epf", "security", "login", "-u", "any_role1", "-p");
		process = builder.start();
		TestUtil.waitUntil(o -> process.isAlive(), Duration.ofSeconds(10));
		Files.write(in, List.of("any_role"), Charset.forName("UTF-8"));
		TestUtil.waitUntil(o -> !process.isAlive(), Duration.ofSeconds(10));
		List<String> lines = Files.readAllLines(out);
		Assert.assertEquals(2, lines.size());
		Assert.assertEquals("Enter value for --password (Password): ", lines.get(0));
		Assert.assertTrue(lines.get(1).length() > 256);
	}

}
