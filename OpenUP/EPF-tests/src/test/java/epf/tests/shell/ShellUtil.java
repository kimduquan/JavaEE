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
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
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
		System.out.println(String.join(" ", builder.command()));
		final Process process = builder.start();
		TestUtil.waitUntil(o -> process.isAlive(), Duration.ofSeconds(10));
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
		Files.write(in, List.of(inputs), Charset.forName("UTF-8"));
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
}
