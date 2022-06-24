package epf.function.git;

import java.nio.file.Paths;
import org.apache.karaf.shell.api.action.Argument;
import org.apache.karaf.shell.api.action.Command;
import org.apache.karaf.shell.api.action.lifecycle.Service;
import org.eclipse.jgit.api.Git;
import epf.function.git.internal.GitFunction;

/**
 * 
 */
@Command(scope = "epf", name = "git-init")
@Service
public class InitFunction extends GitFunction<Git> {
	
	/**
	 *
	 */
	@Argument
    private boolean bare;
	
	/**
	 *
	 */
	@Argument
	private String directory;
	
	/**
	 *
	 */
	@Argument
	private String gitDir;
	
	/**
	 *
	 */
	@Argument
	private String initialBranch;

	@Override
	public Git call() throws Exception {
		final Git git =  Git.init()
				.setBare(bare).setDirectory(Paths.get(directory).toFile())
				.setGitDir(Paths.get(gitDir).toFile())
				.setInitialBranch(initialBranch)
				.call();
		addGit(git);
		return git;
	}
}
