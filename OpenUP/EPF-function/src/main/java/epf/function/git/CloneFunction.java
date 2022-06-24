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
@Command(scope = "epf", name = "git-clone")
@Service
public class CloneFunction extends GitFunction<Git> {
	
	/**
	 *
	 */
	@Argument
    private boolean bare;
	
	/**
	 *
	 */
	@Argument
	private String branch;
	
	/**
	 *
	 */
	@Argument
	private boolean cloneAllBranches;
	
	/**
	 *
	 */
	@Argument
	private boolean cloneSubmodules;
	
	/**
	 *
	 */
	@Argument
	private String directory;
	
	/**
	 *
	 */
	@Argument
	private String gidDir;
	
	/**
	 *
	 */
	@Argument
	private boolean mirror;
	
	/**
	 *
	 */
	@Argument
	private boolean noCheckout;
	
	/**
	 *
	 */
	@Argument
	private String remote;
	
	/**
	 *
	 */
	@Argument
	private int timeout;
	
	/**
	 *
	 */
	@Argument
	private String uri;

	@Override
	public Git call() throws Exception {
		final Git git = Git.cloneRepository()
				.setBare(bare)
				.setBranch(branch)
				.setCloneAllBranches(cloneAllBranches)
				.setCloneSubmodules(cloneSubmodules)
				.setDirectory(Paths.get(directory).toFile())
				.setGitDir(Paths.get(gidDir).toFile())
				.setMirror(mirror)
				.setNoCheckout(noCheckout)
				.setNoTags()
				.setRemote(remote)
				.setTimeout(timeout)
				.setURI(uri)
				.call();
		addGit(git);
		return git;
	}
}
