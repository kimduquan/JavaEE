package epf.function.git.internal;

import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;
import org.apache.karaf.shell.api.action.Action;
import org.eclipse.jgit.api.Git;

/**
 * @param <T>
 */
public abstract class GitFunction<T> implements Callable<T>, Action {
	
	/**
	 *
	 */
	private transient final static Queue<Git> gits = new ConcurrentLinkedQueue<>();
	
	/**
	 * @return
	 */
	protected static Git getGit() {
		return gits.element();
	}
	
	/**
	 * @param git
	 */
	protected static void addGit(final Git git) {
		gits.add(git);
	}
	
	protected static Git removeGit() {
		return gits.remove();
	}

	@Override
	public Object execute() throws Exception {
		return call();
	}
}
