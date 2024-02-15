package epf.messaging.schema;

import jakarta.nosql.Column;
import jakarta.nosql.Entity;

/**
 * 
 */
@Entity
public class Topic extends Destination {

	/**
	 * 
	 */
	@Column
	private String topicName​;

	public String getTopicName​() {
		return topicName​;
	}

	public void setTopicName​(final String topicName​) {
		this.topicName​ = topicName​;
	}
}
