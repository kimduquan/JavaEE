package epf.concurrent.client;

import java.net.URI;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * 
 */
@ApplicationScoped
public class Concurrent {
	
	/**
	 * 
	 */
	private transient final Queue<Synchronized> sessions = new ConcurrentLinkedQueue<>();
	
	/**
	 * 
	 */
	private URI serverEndpoint;
	
	private void newSynchronized() throws Exception {
		final Synchronized synchronized_ = new Synchronized(serverEndpoint);
		synchronized_.connectToServer();
		sessions.add(synchronized_);
	}
	
	/**
	 * @param uri
	 */
	protected void setServerEndpoint(final URI uri) {
		this.serverEndpoint = uri;
	}
	
	/**
	 * @return
	 * @throws Exception
	 */
	public Synchronized try_() throws Exception {
		final Queue<Synchronized> synchronizedSessions = new ConcurrentLinkedQueue<>();
		Synchronized synchronized_ = null;
		do {
			synchronized_ = sessions.poll();
			if(synchronized_ != null) {
				if(synchronized_.isSynchronized()) {
					synchronizedSessions.add(synchronized_);
					synchronized_ = null;
				}
				else {
					synchronized_.try_();
					while(!synchronizedSessions.isEmpty()) {
						sessions.add(synchronizedSessions.poll());
					}
					break;
				}
			}
			else {
				newSynchronized();
			}
		}
		while(synchronized_ == null);
		return synchronized_;
	}
	
	/**
	 * @param synchronized_
	 */
	public void finally_(final Synchronized synchronized_) {
		synchronized_.finally_();
		sessions.add(synchronized_);
	}
}
