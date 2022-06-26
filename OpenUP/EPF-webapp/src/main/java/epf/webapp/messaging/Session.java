package epf.webapp.messaging;

import java.io.Serializable;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.eclipse.microprofile.jwt.Claims;
import epf.security.auth.core.StandardClaims;
import epf.webapp.PrincipalStore;
import epf.webapp.internal.ConfigSource;
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
	private transient ConfigSource config;
	
	/**
	 * 
	 */
	@Inject
	private transient PrincipalStore userStore;
	
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
	public String getReplyTo(final Message message) {
		final Map<String, Object> userInfo = userStore.getClaims(message.getReplyTo());
		Object name = userInfo.get(StandardClaims.name.name());
		if(name == null) {
			name = userInfo.get(Claims.full_name.name());
		}
		return String.valueOf(name);
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
	 * @param message
	 * @return
	 */
	public String getPicture(final Message message) {
		final Map<String, Object> userInfo = userStore.getClaims(message.getReplyTo());
		final Object picture = userInfo.get(StandardClaims.picture.name());
		return picture != null ? (String)picture : "";
	}
}
