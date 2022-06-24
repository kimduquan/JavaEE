package epf.function.git;

import org.apache.karaf.shell.api.action.Argument;
import org.apache.karaf.shell.api.action.Command;
import org.apache.karaf.shell.api.action.lifecycle.Service;
import org.eclipse.jgit.api.CreateBranchCommand.SetupUpstreamMode;
import org.eclipse.jgit.lib.Ref;
import epf.function.git.internal.GitFunction;

/**
 * 
 */
@Command(scope = "epf", name = "git-create")
@Service
public class CreateBranchFunction extends GitFunction<Ref> {
	
	/**
	 *
	 */
	@Argument
	private boolean force;
	
	/**
	 *
	 */
	@Argument
	private String name;
	
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
		return getGit().branchCreate()
				.setForce(force)
				.setName(name)
				.setStartPoint(startPoint)
				.setUpstreamMode(SetupUpstreamMode.valueOf(upstreamMode))
				.call();
	}
}
