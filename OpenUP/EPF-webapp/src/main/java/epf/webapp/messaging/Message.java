package epf.webapp.messaging;

import java.util.Map;

/**
 * @author PC
 *
 */
public class Message {

	/**
	 * 
	 */
	private Map<String, Object> properties;
	/**
	 * 
	 */
	private String correlationID;
	/**
	 * 
	 */
	private Integer deliveryMode;
	/**
	 * 
	 */
	private Long deliveryTime;
	/**
	 * 
	 */
	private String destination;
	/**
	 * 
	 */
	private Long expiration;
	/**
	 * 
	 */
	private String messageID;
	/**
	 * 
	 */
	private Integer priority;
	/**
	 * 
	 */
	private Boolean redelivered;
	/**
	 * 
	 */
	private String replyTo;
	/**
	 * 
	 */
	private Long timestamp;
	/**
	 * 
	 */
	private String type;
	
	public Map<String, Object> getProperties() {
		return properties;
	}
	public void setProperties(final Map<String, Object> properties) {
		this.properties = properties;
	}
	public String getCorrelationID() {
		return correlationID;
	}
	public void setCorrelationID(final String correlationID) {
		this.correlationID = correlationID;
	}
	public Integer getDeliveryMode() {
		return deliveryMode;
	}
	public void setDeliveryMode(final Integer deliveryMode) {
		this.deliveryMode = deliveryMode;
	}
	public Long getDeliveryTime() {
		return deliveryTime;
	}
	public void setDeliveryTime(final Long deliveryTime) {
		this.deliveryTime = deliveryTime;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(final String destination) {
		this.destination = destination;
	}
	public Long getExpiration() {
		return expiration;
	}
	public void setExpiration(final Long expiration) {
		this.expiration = expiration;
	}
	public String getMessageID() {
		return messageID;
	}
	public void setMessageID(final String messageID) {
		this.messageID = messageID;
	}
	public Integer getPriority() {
		return priority;
	}
	public void setPriority(final Integer priority) {
		this.priority = priority;
	}
	public Boolean getRedelivered() {
		return redelivered;
	}
	public void setRedelivered(final Boolean redelivered) {
		this.redelivered = redelivered;
	}
	public String getReplyTo() {
		return replyTo;
	}
	public void setReplyTo(final String replyTo) {
		this.replyTo = replyTo;
	}
	public Long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(final Long timestamp) {
		this.timestamp = timestamp;
	}
	public String getType() {
		return type;
	}
	public void setType(final String type) {
		this.type = type;
	}
}
