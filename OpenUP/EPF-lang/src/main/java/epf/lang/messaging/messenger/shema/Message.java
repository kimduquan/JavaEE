package epf.lang.messaging.messenger.shema;

public class Message {

	private ObjectRef sender;
	private ObjectRef recipient;
	private long timestamp;
	private Text message;
	
	public ObjectRef getSender() {
		return sender;
	}
	public void setSender(ObjectRef sender) {
		this.sender = sender;
	}
	public ObjectRef getRecipient() {
		return recipient;
	}
	public void setRecipient(ObjectRef recipient) {
		this.recipient = recipient;
	}
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	public Text getMessage() {
		return message;
	}
	public void setMessage(Text message) {
		this.message = message;
	}
}
