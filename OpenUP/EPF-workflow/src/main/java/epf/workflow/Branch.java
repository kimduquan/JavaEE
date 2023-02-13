package epf.workflow;

import java.util.concurrent.Callable;
import epf.workflow.schema.ParallelStateBranch;

/**
 * @author PC
 *
 */
public class Branch implements Callable<Void> {
	
	/**
	 * 
	 */
	private final ParallelStateBranch branch;
	
	/**
	 * @param branch
	 */
	public Branch(final ParallelStateBranch branch) {
		this.branch = branch;
	}
	
	@Override
	public Void call() throws Exception {
		return null;
	}

	public ParallelStateBranch getBranch() {
		return branch;
	}
}
