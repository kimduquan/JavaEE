package epf.workflow.event;

import epf.workflow.event.mapping.UUIDAttributeConverter;
import jakarta.nosql.mapping.Column;
import jakarta.nosql.mapping.Convert;
import jakarta.nosql.mapping.Id;
import jakarta.nosql.mapping.MappedSuperclass;

/**
 * @author PC
 *
 */
@MappedSuperclass
public class Event {

	/**
	 * 
	 */
	@Id("id")
	@Convert(UUIDAttributeConverter.class)
	private String id;
	
	/**
	 * 
	 */
	@Column
	private String source;
	
	/**
	 * 
	 */
	@Column
	private String specVersion;
	
	/**
	 * 
	 */
	@Column
	private String type;
	
	/**
	 * 
	 */
	@Column
	private String dataContentType;
	
	/**
	 * 
	 */
	@Column
	private String subject;
	
	/**
	 * 
	 */
	@Column
	private String time;
	
	/**
	 * 
	 */
	@Column
	private Object data;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
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
