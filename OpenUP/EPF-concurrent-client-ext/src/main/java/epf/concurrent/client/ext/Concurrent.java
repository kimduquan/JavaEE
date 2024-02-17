package epf.concurrent.client.ext;

import java.net.URI;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.annotation.PreDestroy;
import epf.util.logging.LogManager;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * 
 */
@ApplicationScoped
public class Concurrent {
	
	/**
	 * 
	 */
	private transient static final Logger LOGGER = LogManager.getLogger(Concurrent.class.getName());
	
	/**
	 * 
	 */
	private transient final Queue<Synchronized> sessions = new ConcurrentLinkedQueue<>();
	
	/**
	 * 
	 */
	private URI serverEndpoint;
	
	/**
	 * 
	 */
	@PreDestroy
	protected void close() {
		sessions.forEach(session -> {
			try {
				session.close();
			} 
			catch (Exception e) {
				LOGGER.log(Level.WARNING, "close", e);
			}
		});
	}
	
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
		synchronized_.try_();
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
