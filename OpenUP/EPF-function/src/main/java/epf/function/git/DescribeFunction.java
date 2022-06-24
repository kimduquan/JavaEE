package epf.function.git;

import org.apache.karaf.shell.api.action.Argument;
import org.apache.karaf.shell.api.action.Command;
import org.apache.karaf.shell.api.action.lifecycle.Service;
import epf.function.git.internal.GitFunction;

/**
 * 
 */
@Command(scope = "epf", name = "git-describe")
@Service
public class DescribeFunction extends GitFunction<String> {
	
	/**
	 *
	 */
	@Argument
	private int abbrev;
	
	/**
	 *
	 */
	@Argument
	private boolean all;
	
	/**
	 *
	 */
	@Argument
	private boolean always;
	
	/**
	 *
	 */
	@Argument
	private boolean _long;
	
	/**
	 *
	 */
	@Argument
	private String[] match;
	
	/**
	 *
	 */
	@Argument
	private boolean tags;
	
	/**
	 *
	 */
	@Argument
	private String target;

	@Override
	public String call() throws Exception {
		return getGit().describe()
				.setAbbrev(abbrev)
				.setAll(all)
				.setAlways(always)
				.setLong(_long)
				.setMatch(match)
				.setTags(tags)
				.setTarget(target)
				.call();
	}

}
