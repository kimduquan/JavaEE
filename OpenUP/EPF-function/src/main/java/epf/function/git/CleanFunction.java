package epf.function.git;

import java.util.Set;
import org.apache.karaf.shell.api.action.Argument;
import org.apache.karaf.shell.api.action.Command;
import org.apache.karaf.shell.api.action.lifecycle.Service;
import epf.function.git.internal.GitFunction;

/**
 * 
 */
@Command(scope = "epf", name = "git-clean")
@Service
public class CleanFunction extends GitFunction<Set<String>> {
	
	/**
	 *
	 */
	@Argument
	private boolean cleanDirectories;
	
	/**
	 *
	 */
	@Argument
	private boolean dryRun;
	
	/**
	 *
	 */
	@Argument
	private boolean force;

	@Override
	public Set<String> call() throws Exception {
		return getGit().clean()
				.setCleanDirectories(cleanDirectories)
				.setDryRun(dryRun)
				.setForce(force)
				.call();
	}

}
