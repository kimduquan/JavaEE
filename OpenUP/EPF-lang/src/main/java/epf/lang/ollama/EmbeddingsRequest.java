package epf.lang.ollama;

/**
 * 
 */
public class EmbeddingsRequest {

	private String model;
	private String prompt;
	
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
}
