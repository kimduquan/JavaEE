package epf.function.git;

import org.eclipse.jgit.api.Git;
import epf.function.internal.EPFTask;

/**
 * @author PC
 *
 */
public abstract class GitTask<T> extends EPFTask<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * @return
	 */
	public Git getGit() {
		return ((GitJob)getEPFJob()).getGit();
	}
}
