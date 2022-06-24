package epf.function.git;

import org.apache.karaf.shell.api.action.Argument;
import org.apache.karaf.shell.api.action.Command;
import org.apache.karaf.shell.api.action.lifecycle.Service;
import org.eclipse.jgit.api.CheckoutCommand.Stage;
import org.eclipse.jgit.api.CreateBranchCommand.SetupUpstreamMode;
import org.eclipse.jgit.lib.Ref;
import epf.function.git.internal.GitFunction;

/**
 * 
 */
@Command(scope = "epf", name = "git-checkout")
@Service
public class CheckoutFunction extends GitFunction<Ref> {
	
	/**
	 *
	 */
	@Argument
	private boolean allPaths;
	
	/**
	 *
	 */
	@Argument
	private boolean createBranch;
	
	/**
	 *
	 */
	@Argument
	private boolean forced;
	
	/**
	 *
	 */
	@Argument
	private boolean forceRefUpdate;
	
	/**
	 *
	 */
	@Argument
	private String name;
	
	/**
	 *
	 */
	@Argument
	private boolean orphan;
	
	/**
	 *
	 */
	@Argument
	private String stage;
	
	/**
	 *
	 */
	@Argument
	private String startPoint;
	
	/**
	 *
	 */
	@Argument
	private String upstreamMode;

	@Override
	public Ref call() throws Exception {
		return getGit().checkout()
				.setAllPaths(allPaths)
				.setCreateBranch(createBranch)
				.setForced(forced)
				.setForceRefUpdate(forceRefUpdate)
				.setName(name)
				.setOrphan(orphan)
				.setStage(Stage.valueOf(stage))
				.setStartPoint(startPoint)
				.setUpstreamMode(SetupUpstreamMode.valueOf(upstreamMode))
				.call();
	}

}
