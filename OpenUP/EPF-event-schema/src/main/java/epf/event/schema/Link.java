package epf.event.schema;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.eclipse.jnosql.mapping.Convert;
import epf.naming.Naming.Event.Schema;
import epf.nosql.schema.util.UUIDAttributeConverter;
import jakarta.nosql.Column;
import jakarta.nosql.Entity;
import jakarta.nosql.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Link {
	
	@Id(Schema.ID)
	@Convert(UUIDAttributeConverter.class)
	@NotNull
	@NotBlank
	private String id;
	
	@Column
	private String rel;
	
	@Column
	private String title;
	
	@Column
	private String type;
	
	@Column
	private Map<String, String> params;
	
	@Column
	private String uri;

	public String getId() {
		return id;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public String getRel() {
		return rel;
	}

	public void setRel(final String rel) {
		this.rel = rel;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	public String getType() {
		return type;
	}

	public void setType(final String type) {
		this.type = type;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(final String uri) {
		this.uri = uri;
	}

	public Map<String, String> getParams() {
		return params;
	}

	public void setParams(final Map<String, String> params) {
		this.params = params;
	}
	
	/**
	 * @param map
	 * @return
	 */
	public static Link of(final Map<String, String> map) {
		Objects.requireNonNull(map, "Map");
		final Link link = new Link();
		final Map<String, String> params = new HashMap<>(map);
		link.rel = params.remove("rel");
		link.title = params.remove("title");
		link.type = params.remove("type");
		link.uri = params.remove("uri");
		link.params = params;
		return link;
	}
	
	/**
	 * @return
	 */
	public Map<String, Object> toMap() {
		final Map<String, Object> map = new HashMap<>();
		if(params != null) {
			map.putAll(params);
		}
		if(rel != null) {
			map.put("rel", rel);
		}
		if(title != null) {
			map.put("title", title);
		}
		if(type != null) {
			map.put("type", type);
		}
		if(uri != null) {
			map.put("uri", uri);
		}
		return map;
	}
}
