package epf.function.git;

import java.util.List;
import org.apache.karaf.shell.api.action.Argument;
import org.apache.karaf.shell.api.action.Command;
import org.apache.karaf.shell.api.action.lifecycle.Service;
import org.eclipse.jgit.api.ListBranchCommand.ListMode;
import org.eclipse.jgit.lib.Ref;
import epf.function.git.internal.GitFunction;

/**
 * 
 */
@Command(scope = "epf", name = "git-list")
@Service
public class ListBranchFunction extends GitFunction<List<Ref>> {
	
	/**
	 *
	 */
	@Argument
	private String contains;
	
	/**
	 *
	 */
	@Argument
	private String listMode;

	@Override
	public List<Ref> call() throws Exception {
		return getGit().branchList()
				.setContains(contains)
				.setListMode(ListMode.valueOf(listMode))
				.call();
	}

}
