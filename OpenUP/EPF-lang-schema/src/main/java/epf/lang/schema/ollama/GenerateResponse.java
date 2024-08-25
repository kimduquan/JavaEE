package epf.lang.schema.ollama;

/**
 * 
 */
public class GenerateResponse {

	private String model;
	private String created_at;
	private String response;
	private boolean done;
	private int[] context;
	
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getCreated_at() {
		return created_at;
	}
	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}
	public boolean isDone() {
		return done;
	}
	public void setDone(boolean done) {
		this.done = done;
	}
	public int[] getContext() {
		return context;
	}
	public void setContext(int[] context) {
		this.context = context;
	}
}
