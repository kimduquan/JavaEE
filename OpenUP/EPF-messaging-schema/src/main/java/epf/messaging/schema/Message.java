package epf.messaging.schema;

import java.util.Map;
import jakarta.nosql.Column;
import jakarta.nosql.Id;
import jakarta.nosql.MappedSuperclass;

@MappedSuperclass
public class Message {

	private Map<String, Object> properties;
	
	@Column
	private String correlationID;
	
	@Column
	private Integer deliveryMode;
	
	@Column
	private Long deliveryTime;
	
	@Column
	private Destination destination;
	
	@Column
	private Long expiration;
	
	@Id("messageID")
	private String messageID;
	
	@Column
	private Integer priority;
	
	@Column
	private Boolean redelivered;
	
	@Column
	private Destination replyTo;
	
	@Column
	private Long timestamp;
	
	@Column
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
	public Destination getDestination() {
		return destination;
	}
	public void setDestination(final Destination destination) {
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
	public Destination getReplyTo() {
		return replyTo;
	}
	public void setReplyTo(final Destination replyTo) {
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
