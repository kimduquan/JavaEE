package epf.function.internal;

import java.util.concurrent.Callable;
import org.apache.karaf.shell.api.action.Action;
import org.jppf.node.protocol.AbstractTask;

/**
 * @author PC
 *
 * @param <T>
 */
public abstract class EPFTask<T> extends AbstractTask<Object> implements Action, Callable<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @return
	 */
	public EPFJob getEPFJob() {
		return (EPFJob) getJob();
	}
	
	@Override
	public void run() {
		try {
			setResult(call());
		}
		catch (Throwable e) {
			setThrowable(e);
		}
	}
	
	@Override
	public Object execute() throws Exception {
		return call();
	}
}
