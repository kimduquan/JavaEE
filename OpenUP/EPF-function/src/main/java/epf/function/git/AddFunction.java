package epf.function.git;

import org.apache.karaf.shell.api.action.Argument;
import org.apache.karaf.shell.api.action.Command;
import org.apache.karaf.shell.api.action.lifecycle.Service;

/**
 * 
 */
@Command(scope = "epf", name = "git-add")
@Service
public class AddFunction extends GitTask<Void> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
	public Void call() throws Exception {
		getGit().add()
		.setUpdate(update)
		.addFilepattern(filepattern)
		.call();
		return null;
	}
}
