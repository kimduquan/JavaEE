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
@Command(scope = "epf", name = "git-open")
@Service
public class OpenFunction extends GitFunction<Git> {
	
	/**
	 *
	 */
	@Argument
    private String dir;

	@Override
	public Git call() throws Exception {
		final Git git =  Git.open(Paths.get(dir).toFile());
		addGit(git);
		return git;
	}
}
