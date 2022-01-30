package epf.messaging;

import java.util.Map;

/**
 * @author PC
 *
 */
public class Message {

	/**
	 * 
	 */
	private String messageID;
	
	/**
	 * 
	 */
	private long timestamp;
	
	/**
	 * 
	 */
	private String correlationID;
	
	/**
	 * 
	 */
	private boolean redelivered;
	
	/**
	 * 
	 */
	private String type;
	
	/**
	 * 
	 */
	private long expiration;
	
	/**
	 * 
	 */
	private long deliveryTime;
	
	/**
	 * 
	 */
	private Map<String, Object> properties;

	public String getMessageID() {
		return messageID;
	}

	public void setMessageID(final String messageID) {
		this.messageID = messageID;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(final long timestamp) {
		this.timestamp = timestamp;
	}

	public String getCorrelationID() {
		return correlationID;
	}

	public void setCorrelationID(final String correlationID) {
		this.correlationID = correlationID;
	}

	public boolean isRedelivered() {
		return redelivered;
	}

	public void setRedelivered(final boolean redelivered) {
		this.redelivered = redelivered;
	}

	public String getType() {
		return type;
	}

	public void setType(final String type) {
		this.type = type;
	}

	public long getExpiration() {
		return expiration;
	}

	public void setExpiration(final long expiration) {
		this.expiration = expiration;
	}

	public long getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(final long deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public Map<String, Object> getProperties() {
		return properties;
	}

	public void setProperties(final Map<String, Object> properties) {
		this.properties = properties;
	}
}
