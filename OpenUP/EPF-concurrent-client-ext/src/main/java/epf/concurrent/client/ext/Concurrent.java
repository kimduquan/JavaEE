package epf.concurrent.client.ext;

import java.net.URI;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Predicate;
import java.util.function.Supplier;
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
	
	private Synchronized poll(final Predicate<? super Synchronized> filter, final Supplier<Synchronized> supplier) throws Exception {
		final Queue<Synchronized> tempSessions = new ConcurrentLinkedQueue<>();
		Synchronized synchronized_ = null;
		do {
			synchronized_ = internalSessions.poll();
			if(synchronized_ != null) {
				if(!filter.test(synchronized_)) {
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
			else if(supplier != null) {
				synchronized_ = supplier.get();
				if(synchronized_ == null) {
					break;
				}
				internalSessions.add(synchronized_);
			}
		}
		while(synchronized_ == null);
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
	public Synchronized synchronized_() throws Exception {
		return poll(sync -> !sync._synchronized(), () -> {
			try {
				final Synchronized synchronized_ = newSynchronized();
				synchronized_.try_();
				return synchronized_;
			} 
			catch (Exception e) {
				return null;
			}
		});
	}
	
	/**
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Synchronized synchronized_(final String id) throws Exception {
		return poll(sync -> sync._synchronized(id), () -> {
			try {
				final Synchronized synchronized_ = newSynchronized();
				synchronized_.try_(id);
				return synchronized_;
			} 
			catch (Exception e) {
				return null;
			}
		});
	}
}
