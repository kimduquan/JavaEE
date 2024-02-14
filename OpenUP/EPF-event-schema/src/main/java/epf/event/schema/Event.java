package epf.event.schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.nosql.Column;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.eclipse.jnosql.mapping.Convert;
import org.eclipse.jnosql.mapping.MappedSuperclass;
import epf.event.schema.util.UUIDAttributeConverter;
import epf.naming.Naming.Event.Schema;
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
	@Id(Schema.ID)
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
	@Column
	private String dataContentType;
	
	/**
	 * 
	 */
	@Column
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
	
	/**
	 * @param map
	 * @param ext
	 * @return
	 */
	public static Event event(final Map<String, Object> map, final Map<String, Object> ext) {
		Objects.requireNonNull(map, "Map");
		final Event event = new Event();
		ext.putAll(map);
		event.setData(ext.remove(Schema.DATA));
		final Object dataContentType = ext.remove(Schema.DATA_CONTENT_TYPE);
		if(dataContentType instanceof String) {
			event.setDataContentType((String)dataContentType);
		}
		final Object dataSchema = ext.remove(Schema.DATA_SCHEMA);
		if(dataSchema instanceof String) {
			event.setDataSchema((String) dataSchema);
		}
		final Object id = ext.remove(Schema.ID);
		if(id instanceof String) {
			event.setId((String) id);
		}
		final Object source = ext.remove(Schema.SOURCE);
		if(source instanceof String) {
			event.setSource((String) source);
		}
		final Object specVersion = ext.remove(Schema.SPEC_VERSION);
		if(specVersion instanceof String) {
			event.setSpecVersion((String) specVersion);
		}
		final Object subject = ext.remove(Schema.SUBJECT);
		if(subject instanceof String) {
			event.setSubject((String) subject);
		}
		final Object time = ext.remove(Schema.TIME);
		if(time instanceof String) {
			event.setTime((String) time);
		}
		final Object type = ext.remove(Schema.TYPE);
		if(type instanceof String) {
			event.setType((String) type);
		}
		return event;
	}
	
	/**
	 * @param ext
	 * @return
	 */
	public Map<String, Object> toMap(final Map<String, Object> ext){
		final Map<String, Object> map = new HashMap<>();
		if(data != null) {
			map.put(Schema.DATA, data);
		}
		if(this.dataContentType != null) {
			map.put(Schema.DATA_CONTENT_TYPE, dataContentType);
		}
		if(this.dataSchema != null) {
			map.put(Schema.DATA_SCHEMA, dataSchema);
		}
		if(this.id != null) {
			map.put(Schema.ID, id);
		}
		if(this.source != null) {
			map.put(Schema.SOURCE, source);
		}
		if(this.specVersion != null) {
			map.put(Schema.SPEC_VERSION, specVersion);
		}
		if(this.subject != null) {
			map.put(Schema.SUBJECT, subject);
		}
		if(this.time != null) {
			map.put(Schema.TIME, time);
		}
		if(this.type != null) {
			map.put(Schema.TYPE, type);
		}
		map.putAll(ext);
		return map;
	}
}
