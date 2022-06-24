package epf.function.git;

import org.apache.karaf.shell.api.action.Argument;
import org.apache.karaf.shell.api.action.Command;
import org.apache.karaf.shell.api.action.lifecycle.Service;
import org.eclipse.jgit.api.CherryPickResult;
import org.eclipse.jgit.merge.ContentMergeStrategy;
import org.eclipse.jgit.merge.MergeStrategy;
import epf.function.git.internal.GitFunction;

/**
 * 
 */
@Command(scope = "epf", name = "git-cherry-pick")
@Service
public class CherryPickFunction extends GitFunction<CherryPickResult> {
	
	/**
	 *
	 */
	@Argument
	private String contentMergeStrategy;
	
	/**
	 *
	 */
	@Argument
	private int mainlineParentNumber;
	
	/**
	 *
	 */
	@Argument
	private boolean noCommit;
	
	/**
	 *
	 */
	@Argument
	private String ourCommitName;
	
	/**
	 *
	 */
	@Argument
	private String reflogPrefix;
	
	/**
	 *
	 */
	@Argument
	private String strategy;

	@Override
	public CherryPickResult call() throws Exception {
		return getGit().cherryPick()
				.setContentMergeStrategy(ContentMergeStrategy.valueOf(contentMergeStrategy))
				.setMainlineParentNumber(mainlineParentNumber)
				.setNoCommit(noCommit)
				.setOurCommitName(ourCommitName)
				.setReflogPrefix(reflogPrefix)
				.setStrategy(MergeStrategy.get(strategy))
				.call();
	}

}
