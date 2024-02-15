package epf.webapp.messaging;

import java.io.Serializable;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import epf.messaging.schema.Message;
import epf.messaging.schema.TextMessage;
import epf.webapp.internal.ConfigUtil;
import epf.webapp.naming.Naming.Messaging;

/**
 * @author PC
 *
 */
@SessionScoped
@Named(Messaging.SESSION)
public class Session implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	private List<String> sessions = new CopyOnWriteArrayList<>();

	/**
	 * 
	 */
	private transient final Queue<Message> messageQueue = new ConcurrentLinkedQueue<>();
	
	/**
	 * 
	 */
	private final List<Message> messages = new CopyOnWriteArrayList<>();
	
	/**
	 * 
	 */
	private long limit;
	
	/**
	 * 
	 */
	private transient AtomicLong skip = new AtomicLong(0);
	
	/**
	 * 
	 */
	@Inject
	private transient ConfigUtil config;
	
	/**
	 * 
	 */
	@PostConstruct
	protected void postConstruct() {
		limit = Long.parseLong(config.getProperty(epf.naming.Naming.WebApp.Messaging.MESSAGES_LIMIT));
	}

	public List<Message> getMessages() {
		return messages;
	}
	
	/**
	 * @return
	 */
	public long getRemainingSize() {
		return messageQueue.size() - messages.size() - skip.get();
	}
	
	/**
	 * @param message
	 */
	public void addMessage(final TextMessage message) {
		messageQueue.add(message);
		messages.clear();
		messages.addAll(messageQueue.stream().skip(skip.get()).limit(limit).collect(Collectors.toList()));
	}
	
	/**
	 * 
	 */
	public void collectMessages() {
		messages.clear();
		messages.addAll(messageQueue.stream().skip(skip.addAndGet(limit)).limit(limit).collect(Collectors.toList()));
	}
	
	/**
	 * @param message
	 * @return
	 */
	public String getDeliveryTime(final Message message) {
		final Instant deliveryTime = Instant.ofEpochMilli(message.getDeliveryTime());
		final Duration duration = Duration.between(deliveryTime, Instant.now());
		return duration.toString().substring(2).toLowerCase();
	}
	
	/**
	 * @param message
	 * @return
	 */
	public String getText(final Message message) {
		if(message instanceof TextMessage) {
			return ((TextMessage)message).getText();
		}
		return "";
	}
	
	/**
	 * @param session
	 */
	public void onOpen(final String session) {
		sessions.add(session);
	}
	
	/**
	 * @param session
	 */
	public void onClose(final String session) {
		sessions.remove(session);
	}
}
