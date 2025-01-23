package epf.lang.schema.ollama;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class ChatRequest implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String model;
	private List<Message> messages;
	private boolean stream = true;
	private List<Tool> tools;
	private String format;
	private Map<String, Object> options;
	private String keep_alive = "5m";
	
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
	public List<Tool> getTools() {
		return tools;
	}
	public void setTools(List<Tool> tools) {
		this.tools = tools;
	}
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	public Map<String, Object> getOptions() {
		return options;
	}
	public void setOptions(Map<String, Object> options) {
		this.options = options;
	}
	public String getKeep_alive() {
		return keep_alive;
	}
	public void setKeep_alive(String keep_alive) {
		this.keep_alive = keep_alive;
	}
}
