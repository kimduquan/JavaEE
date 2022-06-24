package epf.function.git;

import java.util.List;
import org.apache.karaf.shell.api.action.Argument;
import org.apache.karaf.shell.api.action.Command;
import org.apache.karaf.shell.api.action.lifecycle.Service;

import epf.function.git.internal.GitFunction;

/**
 * 
 */
@Command(scope = "epf", name = "git-delete")
@Service
public class DeleteBranchFunction extends GitFunction<List<String>> {
	
	/**
	 *
	 */
	@Argument
	private String[] branchNames;
	
	/**
	 *
	 */
	@Argument
	private boolean force;

	@Override
	public List<String> call() throws Exception {
		return getGit().branchDelete()
				.setBranchNames(branchNames)
				.setForce(force)
				.call();
	}

}
