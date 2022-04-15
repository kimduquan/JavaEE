package epf.messaging.internal;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;

import com.mysql.cj.xdevapi.Session;
import com.mysql.cj.xdevapi.SessionFactory;

/**
 * @author PC
 *
 */
@ApplicationScoped
public class SessionQueue {
	
	/**
	 * 
	 */
	private transient SessionFactory factory;
	
	/**
	 * 
	 */
	private transient final Queue<Session> sessions = new ConcurrentLinkedQueue<>();
	
	/**
	 * 
	 */
	@PostConstruct
	protected void initialize() {
		factory = new SessionFactory();
	}
	
	/**
	 * @return
	 */
	public Session poll() {
		if(sessions.isEmpty()) {
			sessions.add(factory.getSession(""));
		}
		final Session session = sessions.poll();
		if(session == null) {
			return factory.getSession("");
		}
		return session;
	}
	
	/**
	 * @param session
	 */
	public void add(final Session session) {
		sessions.add(session);
	}
}
