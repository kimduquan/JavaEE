package epf.function.git;

import org.apache.karaf.shell.api.action.Argument;
import org.apache.karaf.shell.api.action.Command;
import org.apache.karaf.shell.api.action.lifecycle.Service;
import org.eclipse.jgit.blame.BlameResult;
import org.eclipse.jgit.diff.DiffAlgorithm;
import org.eclipse.jgit.diff.DiffAlgorithm.SupportedAlgorithm;
import org.eclipse.jgit.diff.RawTextComparator;
import org.eclipse.jgit.lib.MutableObjectId;
import epf.function.git.internal.GitFunction;

/**
 * 
 */
@Command(scope = "epf", name = "git-blame")
@Service
public class BlameFunction extends GitFunction<BlameResult> {
	
	/**
	 *
	 */
	@Argument
    private String filePath;
	
	/**
	 *
	 */
	@Argument
	private String diffAlgorithm;
	
	/**
	 *
	 */
	@Argument
	private boolean followFileRenames;
	
	/**
	 *
	 */
	@Argument
	String startCommit;
	
	/**
	 *
	 */
	@Argument
	String textComparator;

	@Override
	public BlameResult call() throws Exception {
		final MutableObjectId startCommitObjectId = new MutableObjectId();
		startCommitObjectId.fromString(startCommit);
		RawTextComparator rawTextComparator = null;
		switch(textComparator) {
			case "DEFAULT":
				rawTextComparator = RawTextComparator.DEFAULT;
				break;
			case "WS_IGNORE_ALL":
				rawTextComparator = RawTextComparator.WS_IGNORE_ALL;
				break;
			case "WS_IGNORE_CHANGE":
				rawTextComparator = RawTextComparator.WS_IGNORE_CHANGE;
				break;
			case "WS_IGNORE_LEADING":
				rawTextComparator = RawTextComparator.WS_IGNORE_LEADING;
				break;
			case "WS_IGNORE_TRAILING":
				rawTextComparator = RawTextComparator.WS_IGNORE_TRAILING;
				break;
		}
		return getGit().blame()
				.setFilePath(filePath)
				.setDiffAlgorithm(DiffAlgorithm.getAlgorithm(SupportedAlgorithm.valueOf(diffAlgorithm)))
				.setFollowFileRenames(followFileRenames)
				.setStartCommit(startCommitObjectId)
				.setTextComparator(rawTextComparator)
		.call();
	}
}
