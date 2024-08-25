package epf.tests.webapp.util;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import epf.file.util.PathUtil;
import epf.naming.Naming;
import epf.security.schema.Token;

/**
 * @author PC
 *
 */
public class ShellUtil {
	
	/**
	 * 
	 */
	private static final String RUNNER = System.getProperty(Naming.Shell.SHELL_RUNNER);
	
	/**
	 * 
	 */
	private static Path shellPath;

	/**
	 * @return
	 */
	public static Path getShellPath() {
		if(shellPath == null) {
			shellPath = PathUtil.of(System.getProperty(Naming.Shell.SHELL_PATH));
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
	
	/**
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public static List<String> getOutput(final Path path) throws Exception{
		return Files.readAllLines(path);
	}
	
	/**
	 * @param builder
	 * @param command
	 * @return
	 */
	static ProcessBuilder command(final ProcessBuilder builder, final String...command) {
		final List<String> cmd = new ArrayList<>();
		cmd.add(RUNNER);
		cmd.addAll(Arrays.asList(command));
		return builder.command(cmd);
	}
}
