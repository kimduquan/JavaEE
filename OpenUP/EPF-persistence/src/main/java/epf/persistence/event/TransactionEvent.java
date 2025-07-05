package epf.persistence.event;

import java.io.Serializable;
import epf.persistence.internal.EntityTransaction;

public class TransactionEvent implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private TransactionEventType eventType;
	private EntityTransaction transaction;

	public EntityTransaction getTransaction() {
		return transaction;
	}

	public void setTransaction(EntityTransaction transaction) {
		this.transaction = transaction;
	}

	public TransactionEventType getEventType() {
		return eventType;
	}

	public void setEventType(TransactionEventType eventType) {
		this.eventType = eventType;
	}
}
