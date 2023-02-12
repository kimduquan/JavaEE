package epf.workflow;

import java.net.URI;

/**
 * @author PC
 *
 */
public class Event {

	/**
	 * 
	 */
	private String id;
	
	/**
	 * 
	 */
	private URI source;
	
	/**
	 * 
	 */
	private String specVersion;
	
	/**
	 * 
	 */
	private String type;
	
	/**
	 * 
	 */
	private String dataContentType;
	
	/**
	 * 
	 */
	private URI dataSchema;
	
	/**
	 * 
	 */
	private String subject;
	
	/**
	 * 
	 */
	private String time;
	
	/**
	 * 
	 */
	private Object data;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public URI getSource() {
		return source;
	}

	public void setSource(URI source) {
		this.source = source;
	}

	public String getSpecVersion() {
		return specVersion;
	}

	public void setSpecVersion(String specVersion) {
		this.specVersion = specVersion;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDataContentType() {
		return dataContentType;
	}

	public void setDataContentType(String dataContentType) {
		this.dataContentType = dataContentType;
	}

	public URI getDataSchema() {
		return dataSchema;
	}

	public void setDataSchema(URI dataSchema) {
		this.dataSchema = dataSchema;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
