package epf.function.git;

import org.apache.karaf.shell.api.action.Argument;
import org.apache.karaf.shell.api.action.Command;
import org.apache.karaf.shell.api.action.lifecycle.Service;
import org.eclipse.jgit.dircache.DirCache;
import epf.function.git.internal.GitFunction;

/**
 * 
 */
@Command(scope = "epf", name = "git-add")
@Service
public class AddFunction extends GitFunction<DirCache> {
	
	/**
	 *
	 */
	@Argument
	private boolean update;
	
	/**
	 *
	 */
	@Argument
	private String filepattern;

	@Override
	public DirCache call() throws Exception {
		return getGit().add()
				.setUpdate(update)
				.addFilepattern(filepattern)
				.call();
	}
	
}
