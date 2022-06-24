package epf.function.git;

import java.util.Collection;
import org.apache.karaf.shell.api.action.Argument;
import org.apache.karaf.shell.api.action.Command;
import org.apache.karaf.shell.api.action.lifecycle.Service;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Ref;
import epf.function.git.internal.GitFunction;

/**
 * 
 */
@Command(scope = "epf", name = "git-ls-remote")
@Service
public class LsRemoteFunction extends GitFunction<Collection<Ref>> {
	
	/**
	 *
	 */
	@Argument
    private boolean heads;
	
	/**
	 *
	 */
	@Argument
	private String remote;
	
	/**
	 *
	 */
	@Argument
	private boolean tags;
	
	/**
	 *
	 */
	@Argument
	private int timeout;
	
	/**
	 *
	 */
	@Argument
	private String uploadPack;

	@Override
	public Collection<Ref> call() throws Exception {
		return Git.lsRemoteRepository()
				.setHeads(heads)
				.setRemote(remote)
				.setTags(tags)
				.setTimeout(timeout)
				.setUploadPack(uploadPack)
				.call();
	}
}
