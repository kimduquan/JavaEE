package epf.function.git;

import java.io.OutputStream;
import org.apache.karaf.shell.api.action.Argument;
import org.apache.karaf.shell.api.action.Command;
import org.apache.karaf.shell.api.action.lifecycle.Service;
import epf.function.git.internal.GitFunction;

/**
 * 
 */
@Command(scope = "epf", name = "git-archive")
@Service
public class ArchiveFunction extends GitFunction<OutputStream> {
	
	/**
	 *
	 */
	@Argument
	private String filename;
	
	/**
	 *
	 */
	@Argument
	private String format;
	
	/**
	 *
	 */
	@Argument
	private String[] paths;
	
	/**
	 *
	 */
	@Argument
	private String prefix;

	@Override
	public OutputStream call() throws Exception {
		return getGit().archive()
				.setFilename(filename)
				.setFormat(format)
				.setPaths(paths)
				.setPrefix(prefix)
				.call();
	}
}
