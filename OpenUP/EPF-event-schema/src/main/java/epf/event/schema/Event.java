package epf.event.schema;

import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.nosql.Column;
import org.eclipse.jnosql.mapping.Convert;
import org.eclipse.jnosql.mapping.MappedSuperclass;
import epf.event.schema.util.UUIDAttributeConverter;
import jakarta.nosql.Id;

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
	@Column("specversion")
	@NotNull
	@NotBlank
	@JsonbProperty("specversion")
	private String specVersion = "1.0";
	
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
	@Column("datacontenttype")
	@JsonbProperty("datacontenttype")
	private String dataContentType;
	
	/**
	 * 
	 */
	@Column("dataschema")
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

	public void setId(final String id) {
		this.id = id;
	}

	public String getSource() {
		return source;
	}

	public void setSource(final String source) {
		this.source = source;
	}

	public String getSpecVersion() {
		return specVersion;
	}

	public void setSpecVersion(final String specVersion) {
		this.specVersion = specVersion;
	}

	public String getType() {
		return type;
	}

	public void setType(final String type) {
		this.type = type;
	}

	public String getDataContentType() {
		return dataContentType;
	}

	public void setDataContentType(final String dataContentType) {
		this.dataContentType = dataContentType;
	}

	public String getDataSchema() {
		return dataSchema;
	}

	public void setDataSchema(final String dataSchema) {
		this.dataSchema = dataSchema;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(final String subject) {
		this.subject = subject;
	}

	public String getTime() {
		return time;
	}

	public void setTime(final String time) {
		this.time = time;
	}

	public Object getData() {
		return data;
	}

	public void setData(final Object data) {
		this.data = data;
	}
}
