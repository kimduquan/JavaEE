package epf.lang.schema.ollama;

import java.util.Map;

public class GenerateRequest {

	private String model;
	private String prompt;
	private String suffix;
	private String[] images;
	private String format = "json";
	private Map<String, Object> options;
	private String system;
	private int[] context;
	private boolean stream = true;
	private boolean raw = false;
	private String keep_alive = "5m";
	
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
	public String getSuffix() {
		return suffix;
	}
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}
	public boolean isStream() {
		return stream;
	}
	public void setStream(boolean stream) {
		this.stream = stream;
	}
	public String getKeep_alive() {
		return keep_alive;
	}
	public void setKeep_alive(String keep_alive) {
		this.keep_alive = keep_alive;
	}
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	public String getSystem() {
		return system;
	}
	public void setSystem(String system) {
		this.system = system;
	}
	public Map<String, Object> getOptions() {
		return options;
	}
	public void setOptions(Map<String, Object> options) {
		this.options = options;
	}
}
