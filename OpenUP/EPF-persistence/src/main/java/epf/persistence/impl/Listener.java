package epf.persistence.impl;

import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import org.eclipse.microprofile.context.ManagedExecutor;
import org.eclipse.microprofile.reactive.messaging.Outgoing;
import org.eclipse.microprofile.reactive.streams.operators.PublisherBuilder;
import epf.messaging.util.PublisherUtil;
import epf.messaging.util.reactive.ObjectPublisher;
import epf.naming.Naming;
import epf.schema.EntityEvent;
import epf.schema.PostLoad;
import epf.schema.PostPersist;
import epf.schema.PostRemove;
import epf.schema.PostUpdate;
import epf.util.logging.LogManager;

/**
 * @author PC
 *
 */
@ApplicationScoped
public class Listener {
	
	/**
	 * 
	 */
	private static final Logger LOGGER = LogManager.getLogger(Listener.class.getName());
	
	/**
	 * 
	 */
	private transient ObjectPublisher<EntityEvent> publisher;
	
	/**
	 * 
	 */
	private transient ObjectPublisher<PostLoad> postLoadPublisher;
	
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
		publisher = new ObjectPublisher<EntityEvent>(executor);
		postLoadPublisher = new ObjectPublisher<PostLoad>(executor);
	}
	
	/**
	 * 
	 */
	@PreDestroy
	protected void preDestroy() {
		try {
			publisher.close();
			postLoadPublisher.close();
		}
		catch(Exception ex) {
			LOGGER.throwing(getClass().getName(), "preDestroy", ex);
		}
	}
	
	/**
	 * @param event
	 */
	protected void submit(final EntityEvent event) {
		try(Jsonb jsonb = JsonbBuilder.create()){
			jsonb.toJson(event);
		} 
		catch (Exception e) {
			LOGGER.throwing(getClass().getName(), "submit", e);
		}
		publisher.submit(event);
	}
	
	/**
	 * @param event
	 */
	public void postPersist(@Observes final PostPersist event) {
		submit(event);
	}
	
	/**
	 * @param event
	 */
	public void postRemove(@Observes final PostRemove event) {
		submit(event);
	}
	
	/**
	 * @param event
	 */
	public void postUpdate(@Observes final PostUpdate event) {
		submit(event);
	}
	
	/**
	 * @param event
	 */
	public void postLoad(@Observes final PostLoad event) {
		try(Jsonb jsonb = JsonbBuilder.create()){
			jsonb.toJson(event);
		} 
		catch (Exception e) {
			LOGGER.throwing(getClass().getName(), "postLoad", e);
		}
		postLoadPublisher.submit(event);
	}
	
	@Outgoing(Naming.Persistence.PERSISTENCE_ENTITY_LISTENERS)
    public PublisherBuilder<EntityEvent> getPublisher(){
		return PublisherUtil.newPublisher(publisher);
	}
	
	@Outgoing(Naming.Persistence.PERSISTENCE_ENTITY_LISTENERS_POSTLOAD)
    public PublisherBuilder<PostLoad> getPostLoadPublisher(){
		return PublisherUtil.newPublisher(postLoadPublisher);
	}
}
