package epf.function.git;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.apache.karaf.shell.api.action.Argument;
import org.apache.karaf.shell.api.action.Command;
import org.apache.karaf.shell.api.action.lifecycle.Service;
import org.eclipse.jgit.api.ApplyResult;
import epf.function.git.internal.GitFunction;

/**
 * 
 */
@Command(scope = "epf", name = "git-apply")
@Service
public class ApplyFunction extends GitFunction<ApplyResult> {
	
	/**
	 *
	 */
	@Argument
	private String patch;

	@Override
	public ApplyResult call() throws Exception {
		try(InputStream input = Files.newInputStream(Paths.get(patch))){
			return getGit().apply()
					.setPatch(input)
					.call();
		}
	}
}
