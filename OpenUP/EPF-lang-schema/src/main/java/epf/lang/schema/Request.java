package epf.lang.schema;

import java.util.List;

/**
 * 
 */
public class Request {

	private String model;
	private List<Message> messages;
	
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
}
