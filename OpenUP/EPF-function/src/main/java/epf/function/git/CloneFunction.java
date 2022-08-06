package epf.function.git;

import java.nio.file.Paths;
import org.apache.karaf.shell.api.action.Argument;
import org.apache.karaf.shell.api.action.Command;
import org.apache.karaf.shell.api.action.lifecycle.Service;
import org.eclipse.jgit.api.Git;

/**
 * 
 */
@Command(scope = "epf", name = "git-clone")
@Service
public class CloneFunction extends GitTask<Void> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
	private String gitDir;
	
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
	public Void call() throws Exception {
		Git.cloneRepository()
				.setBare(bare)
				.setBranch(branch)
				.setCloneAllBranches(cloneAllBranches)
				.setCloneSubmodules(cloneSubmodules)
				.setDirectory(Paths.get(directory).toFile())
				.setGitDir(Paths.get(gitDir).toFile())
				.setMirror(mirror)
				.setNoCheckout(noCheckout)
				.setNoTags()
				.setRemote(remote)
				.setTimeout(timeout)
				.setURI(uri)
				.call();
		return null;
	}
}
