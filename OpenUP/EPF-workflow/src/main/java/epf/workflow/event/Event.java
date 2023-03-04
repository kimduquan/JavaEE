package epf.workflow.event;

import javax.json.bind.annotation.JsonbProperty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
	@NotNull
	@NotBlank
	private String id;
	
	/**
	 * 
	 */
	@Column
	@NotNull
	@NotBlank
	private String source;
	
	/**
	 * 
	 */
	@Column
	@NotNull
	@NotBlank
	@JsonbProperty("specversion")
	private String specVersion;
	
	/**
	 * 
	 */
	@Column
	@NotNull
	@NotBlank
	private String type;
	
	/**
	 * 
	 */
	@Column
	@JsonbProperty("datacontenttype")
	private String dataContentType;
	
	/**
	 * 
	 */
	@Column
	@JsonbProperty("dataschema")
	private String dataSchema;
	
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

	public String getDataSchema() {
		return dataSchema;
	}

	public void setDataSchema(String dataSchema) {
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
