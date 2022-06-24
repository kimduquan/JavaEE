package epf.function.git;

import org.apache.karaf.shell.api.action.Command;
import org.apache.karaf.shell.api.action.lifecycle.Service;
import epf.function.git.internal.GitFunction;

/**
 * 
 */
@Command(scope = "epf", name = "git-close")
@Service
public class CloseFunction extends GitFunction<Void> {

	@Override
	public Void call() throws Exception {
		removeGit().close();
		return null;
	}
}
