package epf.messaging.internal;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import com.mysql.cj.xdevapi.Session;

/**
 * @author PC
 *
 */
@RequestScoped
public class Request {
	
	/**
	 * 
	 */
	private transient Session session;
	
	/**
	 * 
	 */
	@Inject
	private transient SessionQueue sessions;

	/**
	 * 
	 */
	@PostConstruct
	protected void postConstruct() {
		session = sessions.poll();
	}
	
	/**
	 * 
	 */
	@PreDestroy
	protected void preDestroy() {
		sessions.add(session);
	}
	
	public Session getSession() {
		return session;
	}
}
