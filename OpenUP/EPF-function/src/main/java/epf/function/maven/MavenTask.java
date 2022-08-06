package epf.function.maven;

import java.util.List;
import org.apache.karaf.shell.api.action.Argument;
import org.apache.karaf.shell.api.action.Command;
import org.apache.karaf.shell.api.action.lifecycle.Service;
import org.apache.maven.cli.MavenCli;
import epf.function.internal.EPFTask;

/**
 * @author PC
 *
 */
@Command(scope = "epf", name = "mvn")
@Service
public class MavenTask extends EPFTask<Integer> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	@Argument(multiValued = true)
	private List<String> args;
	
	/**
	 * 
	 */
	private String workingDirectory;

	@Override
	public Integer call() throws Exception {
		MavenCli cli = new MavenCli();
		return cli.doMain(args.toArray(new String[0]), workingDirectory, null, null);
	}
}
