package epf.function.git;

import java.util.List;

import org.apache.karaf.shell.api.action.Argument;
import org.apache.karaf.shell.api.action.Command;
import org.apache.karaf.shell.api.action.lifecycle.Service;
import org.eclipse.jgit.diff.DiffEntry;
import epf.function.git.internal.GitFunction;

/**
 * 
 */
@Command(scope = "epf", name = "git-diff")
@Service
public class DiffFunction extends GitFunction<List<DiffEntry>> {
	
	/**
	 *
	 */
	@Argument
	private boolean cached;
	
	/**
	 *
	 */
	@Argument
	private int contextLines;
	
	/**
	 *
	 */
	@Argument
	private String destinationPrefix;
	
	/**
	 *
	 */
	@Argument
	private boolean showNameAndStatusOnly;
	
	/**
	 *
	 */
	@Argument
	private String sourcePrefix;

	@Override
	public List<DiffEntry> call() throws Exception {
		return getGit().diff()
				.setCached(cached)
				.setContextLines(contextLines)
				.setDestinationPrefix(destinationPrefix)
				.setShowNameAndStatusOnly(showNameAndStatusOnly)
				.setSourcePrefix(sourcePrefix)
				.call();
	}
	
}
