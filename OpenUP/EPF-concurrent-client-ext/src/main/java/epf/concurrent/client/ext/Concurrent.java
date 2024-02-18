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
	private transient final Queue<Synchronized> internalSessions = new ConcurrentLinkedQueue<>();
	
	/**
	 * 
	 */
	private transient Synchronized default_ = null;
	
	/**
	 * 
	 */
	private URI serverEndpoint;
	
	/**
	 * 
	 */
	@PreDestroy
	protected void close() {
		internalSessions.forEach(session -> {
			try {
				session.clear();
			} 
			catch (Exception e) {
				LOGGER.log(Level.WARNING, "close", e);
			}
		});
		try {
			default_.clear();
		} 
		catch (Exception e) {
			LOGGER.log(Level.WARNING, "close", e);
		}
	}
	
	private Synchronized newSynchronized() throws Exception {
		final Synchronized synchronized_ = new Synchronized();
		synchronized_.connectToServer(serverEndpoint);
		return synchronized_;
	}
	
	/**
	 * @param uri
	 */
	public void connectToServer(final URI uri) throws Exception {
		this.serverEndpoint = uri;
		default_ = newSynchronized();
	}
	
	/**
	 * @return
	 * @throws Exception
	 */
	public String new_() throws Exception {
		final Queue<Synchronized> tempSessions = new ConcurrentLinkedQueue<>();
		Synchronized synchronized_ = null;
		do {
			synchronized_ = internalSessions.poll();
			if(synchronized_ != null) {
				if(synchronized_.isSynchronized()) {
					tempSessions.add(synchronized_);
					synchronized_ = null;
				}
				else {
					while(!tempSessions.isEmpty()) {
						internalSessions.add(tempSessions.poll());
					}
					internalSessions.add(synchronized_);
					break;
				}
			}
			else {
				synchronized_ = newSynchronized();
				internalSessions.add(synchronized_);
			}
		}
		while(synchronized_ == null);
		return synchronized_.getId();
	}
	
	/**
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Synchronized try_(final String id) throws Exception {
		Synchronized synchronized_ = internalSessions.stream().filter(sync -> sync.getId().equals(id)).findFirst().get();
		if(synchronized_ == null) {
			synchronized_ = Synchronized.try_(default_, id);
		}
		return synchronized_;
	}
	
	/**
	 * @param id
	 * @throws Exception
	 */
	public void finally_(final String id) throws Exception {
		final Synchronized synchronized_ = try_(id);
		if(synchronized_ != null) {
			synchronized_.finally_();
		}
	}
}
