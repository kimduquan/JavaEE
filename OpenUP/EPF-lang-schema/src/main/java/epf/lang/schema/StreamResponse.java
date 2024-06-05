package epf.lang.schema;

/**
 * 
 */
public class StreamResponse {

	private String model;
	private String created_at;
	private Message message;
	private boolean done;
	
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getCreatedAt() {
		return created_at;
	}
	public void setCreatedAt(String created_at) {
		this.created_at = created_at;
	}
	public Message getMessage() {
		return message;
	}
	public void setMessage(Message message) {
		this.message = message;
	}
	public boolean isDone() {
		return done;
	}
	public void setDone(boolean done) {
		this.done = done;
	}
}
