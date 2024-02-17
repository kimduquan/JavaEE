package epf.concurrent.client;

import java.net.URI;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import epf.util.logging.LogManager;

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
	private transient final Map<String, Synchronized> synchronizedSessions = new ConcurrentHashMap<>();
	
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
				session.clear();
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
	public void setServerEndpoint(final URI uri) {
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
	
	/**
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Synchronized try_(final String id) throws Exception {
		return synchronizedSessions.get(id);
	}
	
	/**
	 * @param id
	 */
	public void finally_(final String id) {
		sessions.add(synchronizedSessions.remove(id));
	}
	
	/**
	 * @param id
	 * @throws Exception
	 */
	public void synchronized_(final String id) throws Exception {
		try(Synchronized sync = try_(id)){
			sync.synchronized_();
		}
		finally {
			finally_(id);
		}
	}
}
