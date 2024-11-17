package epf.lang.messaging.messenger.client.schema;

import epf.lang.messaging.messenger.shema.ObjectRef;

public class ResponseMessage {

	private ObjectRef recipient;
	private String messaging_type = "RESPONSE";
	private Message message;
	
	public ObjectRef getRecipient() {
		return recipient;
	}
	public void setRecipient(ObjectRef recipient) {
		this.recipient = recipient;
	}
	public String getMessaging_type() {
		return messaging_type;
	}
	public void setMessaging_type(String messaging_type) {
		this.messaging_type = messaging_type;
	}
	public Message getMessage() {
		return message;
	}
	public void setMessage(Message message) {
		this.message = message;
	}
}
