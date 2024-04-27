package epf.shell.cache;

import java.io.PrintWriter;
import jakarta.ws.rs.sse.InboundSseEvent;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/**
 * @author PC
 *
 */
public class CacheSubscriber implements Subscriber<InboundSseEvent> {
	
	/**
	 * 
	 */
	private transient final PrintWriter out;
	
	/**
	 * 
	 */
	private transient final PrintWriter err;
	
	/**
	 * @param out
	 * @param err
	 */
	public CacheSubscriber(final PrintWriter out, final PrintWriter err) {
		this.out = out;
		this.err = err;
	}

	@Override
	public void onSubscribe(final Subscription s) {
	}

	@Override
	public void onNext(final InboundSseEvent t) {
		out.println(t.readData());
	}

	@Override
	public void onError(final Throwable t) {
		err.println(t.getMessage());
	}

	@Override
	public void onComplete() {
	}

}
