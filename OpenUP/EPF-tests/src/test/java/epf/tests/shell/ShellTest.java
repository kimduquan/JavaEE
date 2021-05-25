/**
 * 
 */
package epf.tests.shell;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.ws.rs.NotAuthorizedException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import epf.client.gateway.Gateway;
import epf.client.security.Token;
import epf.tests.TestUtil;
import epf.tests.security.SecurityUtil;

/**
 * @author PC
 *
 */
public class ShellTest {
	
	private static Path workingDir;
	private static Path tempDir;
	private static String token;
	private ProcessBuilder builder;
	private Process process;
	Path out;
	Path in;
	Path err;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		workingDir = ShellUtil.getShellPath().toRealPath();
		tempDir = Files.createTempDirectory("temp");
		token = SecurityUtil.login(null, "any_role1", "any_role");
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		tempDir.toFile().delete();
		SecurityUtil.logOut(null, token);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		in = Files.createTempFile(tempDir, "in", "in");
		out = Files.createTempFile(tempDir, "out", "out");
		err = Files.createTempFile(tempDir, "err", "err");
		builder = new ProcessBuilder();
		builder.directory(workingDir.toFile());
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
		Files.lines(err).forEach(System.err::println);
		Files.lines(out).forEach(System.out::println);
		in.toFile().delete();
		out.toFile().delete();
		err.toFile().delete();
	}

	@Test
	public void testSecurity_Login() throws IOException, InterruptedException {
		builder.command("powershell", "./epf", "security", "login", "-u", "any_role1", "-p");
		process = builder.start();
		TestUtil.waitUntil(o -> process.isAlive(), Duration.ofSeconds(10));
		Files.write(in, List.of("any_role"), Charset.forName("UTF-8"));
		process.waitFor(20, TimeUnit.SECONDS);
		List<String> lines = Files.readAllLines(out);
		Assert.assertEquals(2, lines.size());
		Assert.assertEquals("Enter value for --password (Password): ", lines.get(0));
		String newToken = lines.get(1);
		Assert.assertTrue(newToken.length() > 256);
		SecurityUtil.logOut(null, newToken);
		lines = Files.readAllLines(err);
		Assert.assertTrue(lines.isEmpty());
	}

	@Test(expected = NotAuthorizedException.class)
	public void testSecurity_Logout() throws Exception {
		String newToken = SecurityUtil.login(null, "any_role1", "any_role");
		builder.command("powershell", "./epf", "security", "logout", "-t", newToken);
		process = ShellUtil.waitFor(builder);
		List<String> lines = Files.readAllLines(out);
		Assert.assertEquals(2, lines.size());
		Assert.assertEquals("any_role1", lines.get(1));
		SecurityUtil.auth(null, newToken);
		lines = Files.readAllLines(err);
		Assert.assertTrue(lines.isEmpty());
	}
	
	@Test
	public void testSecurity_Auth() throws Exception {
		builder.command("powershell", "./epf", "security", "auth", "-t", token);
		process = ShellUtil.waitFor(builder);
		List<String> lines = Files.readAllLines(out);
		Assert.assertEquals(2, lines.size());
		try(Jsonb jsonb = JsonbBuilder.create()){
			Token securityToken = jsonb.fromJson(lines.get(1), Token.class);
			Assert.assertNotNull("Token", securityToken);
			Assert.assertEquals("Token.name", "any_role1", securityToken.getName());
		}
		lines = Files.readAllLines(err);
		Assert.assertTrue(lines.isEmpty());
	}
	
	@Test
	public void testSecurity_UpdatePassword() throws InterruptedException, IOException {
		builder.command("powershell", "./epf", "security", "update", "-t", token, "-p");
		process = builder.start();
		TestUtil.waitUntil(o -> process.isAlive(), Duration.ofSeconds(10));
		Files.write(in, List.of("any_role"), Charset.forName("UTF-8"));
		process.waitFor(20, TimeUnit.SECONDS);
		List<String> lines = Files.readAllLines(out);
		Assert.assertTrue(lines.isEmpty());
		lines = Files.readAllLines(err);
		Assert.assertTrue(lines.isEmpty());
	}
	
	@Test
	public void testSecurity_Revoke() throws InterruptedException, IOException {
		String token = SecurityUtil.login(null, "any_role1", "any_role");
		builder.command("powershell", "./epf", "security", "revoke", "-t", token);
		process = ShellUtil.waitFor(builder);
		List<String> lines = Files.readAllLines(out);
		Assert.assertEquals(2, lines.size());
		String newToken = lines.get(1);
		Assert.assertNotEquals("", newToken);
		Assert.assertTrue(newToken.length() > 256);
		SecurityUtil.logOut(null, newToken);
	}
}
