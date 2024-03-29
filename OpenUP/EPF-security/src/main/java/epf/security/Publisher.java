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
import epf.messaging.util.PublisherUtil;
import epf.messaging.util.reactive.ObjectPublisher;
import epf.naming.Naming;
import epf.security.schema.Token;
import epf.util.logging.LogManager;

/**
 * @author PC
 *
 */
@ApplicationScoped
public class Publisher {
	
	/**
	 * 
	 */
	private static final Logger LOGGER = LogManager.getLogger(Publisher.class.getName());
	
	/**
	 * 
	 */
	private transient ObjectPublisher<Token> publisher;

	/**
	 * 
	 */
	@Inject
	transient ManagedExecutor executor;
	
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
	@Outgoing(Naming.SECURITY)
	public PublisherBuilder<Token> getPublisher(){
		return PublisherUtil.newPublisher(publisher);
	}
}
