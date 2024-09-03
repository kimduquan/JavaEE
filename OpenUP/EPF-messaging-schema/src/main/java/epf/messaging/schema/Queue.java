package epf.messaging.schema;

import jakarta.nosql.Column;
import jakarta.nosql.Entity;

/**
 * 
 */
@Entity
public class Queue extends Destination {

	/**
	 * 
	 */
	@Column
	private String queueName ;

	public String getQueueName() {
		return queueName;
	}

	public void setQueueName(final String queueName) {
		this.queueName = queueName;
	}
}
