package epf.function.git;

import org.apache.karaf.shell.api.action.Argument;
import org.apache.karaf.shell.api.action.Command;
import org.apache.karaf.shell.api.action.lifecycle.Service;
import org.eclipse.jgit.lib.Ref;
import epf.function.git.internal.GitFunction;

/**
 * 
 */
@Command(scope = "epf", name = "git-rename")
@Service
public class RenameBranchFunction extends GitFunction<Ref> {
	
	/**
	 *
	 */
	@Argument
	private String newName;
	
	/**
	 *
	 */
	@Argument
	private String oldName;

	@Override
	public Ref call() throws Exception {
		return getGit().branchRename()
				.setNewName(newName)
				.setOldName(oldName)
				.call();
	}

}
