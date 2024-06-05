package epf.lang.schema;

import java.util.List;

/**
 * 
 */
public class Request {

	private String model;
	private List<Message> messages;
	private boolean stream = true;
	
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public List<Message> getMessages() {
		return messages;
	}
	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}
	public boolean isStream() {
		return stream;
	}
	public void setStream(boolean stream) {
		this.stream = stream;
	}
}
