package epf.function.git;

import org.apache.karaf.shell.api.action.Argument;
import org.apache.karaf.shell.api.action.Command;
import org.apache.karaf.shell.api.action.lifecycle.Service;
import org.eclipse.jgit.lib.CommitConfig.CleanupMode;
import org.eclipse.jgit.revwalk.RevCommit;
import epf.function.git.internal.GitFunction;

/**
 * 
 */
@Command(scope = "epf", name = "git-commit")
@Service
public class CommitFunction extends GitFunction<RevCommit> {
	
	/**
	 *
	 */
	@Argument
	private boolean all;
	
	/**
	 *
	 */
	@Argument
	private boolean allowEmpty;
	
	/**
	 *
	 */
	@Argument
	private boolean amend;
	
	/**
	 *
	 */
	@Argument
	private String author;
	
	/**
	 *
	 */
	@Argument
	private String cleanupMode;
	
	/**
	 *
	 */
	@Argument
	private String commentCharacter;
	
	/**
	 *
	 */
	@Argument
	private String committer;
	
	/**
	 *
	 */
	@Argument
	private boolean defaultClean;
	
	/**
	 *
	 */
	@Argument
	private boolean insertChangeId;
	
	/**
	 *
	 */
	@Argument
	private String message;
	
	/**
	 *
	 */
	@Argument
	private boolean noVerify;
	
	/**
	 *
	 */
	@Argument
	private String only;
	
	/**
	 *
	 */
	@Argument
	private String reflogComment;
	
	/**
	 *
	 */
	@Argument
	private boolean sign;
	
	/**
	 *
	 */
	@Argument
	private String signingKey;

	@Override
	public RevCommit call() throws Exception {
		final String authorName = author.split("/")[0];
		final String authorEmail = author.split("/")[1];
		final String committerName = committer.split("/")[0];
		final String committerEmail = committer.split("/")[1];
		return getGit().commit()
				.setAll(all)
				.setAllowEmpty(allowEmpty)
				.setAmend(amend)
				.setAuthor(authorName, authorEmail)
				.setCleanupMode(CleanupMode.valueOf(cleanupMode))
				.setCommentCharacter(commentCharacter.charAt(0))
				.setCommitter(committerName, committerEmail)
				.setDefaultClean(defaultClean)
				.setInsertChangeId(insertChangeId)
				.setMessage(message)
				.setNoVerify(noVerify)
				.setOnly(only)
				.setReflogComment(reflogComment)
				.setSign(sign)
				.setSigningKey(signingKey)
				.call();
	}

}
