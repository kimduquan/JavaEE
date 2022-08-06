package epf.function.git;

import org.eclipse.jgit.api.Git;
import org.jppf.client.event.JobEvent;
import epf.function.internal.EPFJob;

/**
 * @author PC
 *
 */
public class GitJob extends EPFJob {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	private transient Git git;

	public Git getGit() {
		return git;
	}
	
	@Override
	public void jobDispatched(final JobEvent event) {
		super.jobDispatched(event);
		if(git == null) {
			try {
				git = Git.open(getTempDir().toFile());
			} 
			catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void jobEnded(final JobEvent event) {
		if(git != null) {
			git.close();
			git = null;
		}
		super.jobEnded(event);
	}
}
