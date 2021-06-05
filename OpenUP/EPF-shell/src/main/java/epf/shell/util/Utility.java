/**
 * 
 */
package epf.shell.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;
import java.util.stream.Stream;
import epf.shell.Function;
import epf.util.logging.Logging;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.ApplicationScoped;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

/**
 * @author PC
 *
 */
@Command(name = "utility")
@ApplicationScoped
@Function
public class Utility {
	
	/**
	 * 
	 */
	private static final Logger LOG = Logging.getLogger(Utility.class.getName());
	
	/**
	 * 
	 */
	private transient Path tempDir;
	
	/**
	 * 
	 */
	@PostConstruct
	protected void postConstruct() {
		try {
			tempDir = Files.createTempDirectory(Path.of(""), "utility");
		} 
		catch (IOException e) {
			LOG.throwing(Files.class.getName(), "createTempDirectory", e);
		}
	}
	
	/**
	 * 
	 */
	@PreDestroy
	protected void preDestroy() {
		tempDir.toFile().delete();
	}

	/**
	 * @param dir
	 * @param env
	 * @param args
	 * @return
	 * @throws IOException
	 * @throws InterruptedException
	 */
	@Command(name = "exec")
	public int exec(
			@Option(names = {"-dir", "--directory"}, description = "Working directory")
			final File dir,
			@Option(names = {"-env", "--envirionment"}, description = "Envirionment variables")
			final String[] env,
			@Option(names = {"-args", "--arguments"}, description = "Arguments")
			final String... args
			) throws IOException, InterruptedException {
		final ProcessBuilder builder = new ProcessBuilder();
		builder.command(args);
		builder.directory(dir);
		final Map<String, String> enVars = new ConcurrentHashMap<>();
		Stream.of(env).forEach(v -> {
			final String[] data = v.split("=");
			if(data.length > 0) {
				enVars.put(data[0], data[1]);
			}
		});
		builder.environment().putAll(enVars);
		builder.inheritIO();
		final Process process = builder.start();
		return process.waitFor();
	}
}
