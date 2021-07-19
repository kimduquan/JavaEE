/**
 * 
 */
package epf.tests.shell;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import epf.client.security.Token;
import epf.tests.TestUtil;
import epf.util.ProcessUtil;
import epf.util.file.PathUtil;

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
			shellPath = PathUtil.of(System.getProperty("epf.shell.path"));
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
		System.out.println(String.join(" ", builder.command()));
		final Process process = builder.start();
		TestUtil.waitUntil(o -> process.isAlive(), Duration.ofSeconds(20));
		process.waitFor(20, TimeUnit.SECONDS);
		return process;
	}
	
	/**
	 * @param builder
	 * @param in
	 * @param inputs
	 * @return
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public static Process waitFor(final ProcessBuilder builder, final Path in, final String...inputs) throws InterruptedException, IOException {
		System.out.println(String.join(" ", builder.command()));
		final Process process = builder.start();
		TestUtil.waitUntil(o -> process.isAlive(), Duration.ofSeconds(10));
		Files.write(in, Arrays.asList(inputs), Charset.forName("UTF-8"));
		process.waitFor(20, TimeUnit.SECONDS);
		return process;
	}
	
	public static void writeJson(Path path, Object object) throws Exception {
		try(Jsonb jsonb = JsonbBuilder.create()){
			try(OutputStream output = Files.newOutputStream(path)){
				jsonb.toJson(object, output);
			}
		}
	}
	
	public static Token securityAuth(ProcessBuilder builder, String token, Path out) throws Exception {
		builder = ProcessUtil.command(builder, "./epf", "security", "auth", "-t", token);
		Process process = ShellUtil.waitFor(builder);
		List<String> lines = Files.readAllLines(out);
		process.destroyForcibly();
		try(Jsonb jsonb = JsonbBuilder.create()){
			return jsonb.fromJson(lines.get(1), Token.class);
		}
	}
	
	public static void securityLogout(ProcessBuilder builder, String tokenID) throws Exception {
		builder = ProcessUtil.command(builder, "./epf", "security", "logout", "-tid", tokenID);
		Process process = ShellUtil.waitFor(builder);
		process.destroyForcibly();
	}
}
