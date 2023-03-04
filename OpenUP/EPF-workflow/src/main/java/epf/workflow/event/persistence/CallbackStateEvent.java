package epf.workflow.event.persistence;

import jakarta.nosql.mapping.Column;
import jakarta.nosql.mapping.Entity;

/**
 * @author PC
 *
 */
@Entity
public class CallbackStateEvent extends WorkflowEvent {

	@Column
	private String callbackState;

	public String getCallbackState() {
		return callbackState;
	}

	public void setCallbackState(String callbackState) {
		this.callbackState = callbackState;
	}
}
