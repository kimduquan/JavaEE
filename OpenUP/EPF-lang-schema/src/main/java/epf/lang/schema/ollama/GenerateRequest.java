package epf.lang.schema.ollama;

/**
 * 
 */
public class GenerateRequest {

	private String model;
	private String prompt;
	private String[] images;
	private int[] context;
	private boolean raw;
	
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getPrompt() {
		return prompt;
	}
	public void setPrompt(String prompt) {
		this.prompt = prompt;
	}
	public String[] getImages() {
		return images;
	}
	public void setImages(String[] images) {
		this.images = images;
	}
	public int[] getContext() {
		return context;
	}
	public void setContext(int[] context) {
		this.context = context;
	}
	public boolean isRaw() {
		return raw;
	}
	public void setRaw(boolean raw) {
		this.raw = raw;
	}
}
