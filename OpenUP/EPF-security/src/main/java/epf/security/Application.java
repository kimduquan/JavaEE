/**
 * 
 */
package epf.security;

import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import org.eclipse.microprofile.context.ManagedExecutor;
import org.eclipse.microprofile.reactive.messaging.Outgoing;
import org.eclipse.microprofile.reactive.streams.operators.PublisherBuilder;
import epf.client.security.Token;
import epf.messaging.util.PublisherUtil;
import epf.messaging.util.reactive.ObjectPublisher;
import epf.util.logging.Logging;

/**
 * @author PC
 *
 */
@ApplicationScoped
public class Application {
	
	/**
	 * 
	 */
	private static final Logger LOGGER = Logging.getLogger(Application.class.getName());
	
	/**
	 * 
	 */
	private transient ObjectPublisher<Token> publisher;

	/**
	 * 
	 */
	@Inject
	private transient ManagedExecutor executor;
	
	/**
	 * 
	 */
	@PostConstruct
	protected void postConstruct() {
		publisher = new ObjectPublisher<Token>(executor);
	}
	
	/**
	 * 
	 */
	@PreDestroy
	protected void preDestroy() {
		try {
			publisher.close();
		} 
		catch (Exception e) {
			LOGGER.throwing(getClass().getName(), "preDestroy", e);
		}
	}
	
	/**
	 * @param token
	 */
	public void login(@Observes final Token token) {
		publisher.submit(token);
	}
	
	/**
	 * @return
	 */
	@Outgoing("security")
	public PublisherBuilder<Token> getPublisher(){
		return PublisherUtil.newPublisher(publisher);
	}
}
