package epf.event.schema;

import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.nosql.Column;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
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
	
	/**
	 * @param map
	 * @param ext
	 * @return
	 */
	public static Event event(final Map<String, Object> map, final Map<String, Object> ext) {
		Objects.requireNonNull(map, "Map");
		final Event event = new Event();
		ext.putAll(map);
		event.setData(ext.remove("data"));
		if(ext.containsKey("dataContentType")) {
			final Object dataContentType = ext.remove("dataContentType");
			if(dataContentType instanceof String) {
				event.setDataContentType((String)dataContentType);
			}
		}
		if(ext.containsKey("dataSchema")) {
			final Object dataSchema = ext.remove("dataSchema");
			if(dataSchema instanceof String) {
				event.setDataSchema((String) dataSchema);
			}
		}
		if(ext.containsKey("id")) {
			final Object id = ext.remove("id");
			if(id instanceof String) {
				event.setId((String) id);
			}
		}
		if(ext.containsKey("source")) {
			final Object source = ext.remove("source");
			if(source instanceof String) {
				event.setSource((String) source);
			}
		}
		if(ext.containsKey("specVersion")) {
			final Object specVersion = ext.remove("specVersion");
			if(specVersion instanceof String) {
				event.setSpecVersion((String) specVersion);
			}
		}
		if(ext.containsKey("subject")) {
			final Object subject = ext.remove("subject");
			if(subject instanceof String) {
				event.setSubject((String) subject);
			}
		}
		if(ext.containsKey("time")) {
			final Object time = ext.remove("time");
			if(time instanceof String) {
				event.setTime((String) time);
			}
		}
		if(ext.containsKey("type")) {
			final Object type = ext.remove("type");
			if(type instanceof String) {
				event.setType((String) type);
			}
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
			map.put("data", data);
		}
		if(this.dataContentType != null) {
			map.put("dataContentType", dataContentType);
		}
		if(this.dataSchema != null) {
			map.put("dataSchema", dataSchema);
		}
		if(this.id != null) {
			map.put("id", id);
		}
		if(this.source != null) {
			map.put("source", source);
		}
		if(this.specVersion != null) {
			map.put("specVersion", specVersion);
		}
		if(this.subject != null) {
			map.put("subject", subject);
		}
		if(this.time != null) {
			map.put("time", time);
		}
		if(this.type != null) {
			map.put("type", type);
		}
		map.putAll(ext);
		return map;
	}
}
