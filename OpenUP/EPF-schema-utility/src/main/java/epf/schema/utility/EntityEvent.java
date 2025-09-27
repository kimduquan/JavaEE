package epf.schema.utility;

import java.io.Serializable;
import java.time.Instant;
import jakarta.json.bind.annotation.JsonbTypeAdapter;
import epf.util.json.ext.Adapter;

public class EntityEvent implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long time;
	
	private String organization;
	
	private String schema;
	
	private String name;
	
	@JsonbTypeAdapter(Adapter.class)
	private Object entity;
	
	@Override
	public String toString() {
		return String.format("[%dms]%s/%s/%s", Instant.now().toEpochMilli() - time, schema, name, entity);
	}

	public Object getEntity() {
		return entity;
	}

	public void setEntity(final Object entity) {
		this.entity = entity;
	}

	public Long getTime() {
		return time;
	}

	public void setTime(final Long time) {
		this.time = time;
	}

	public String getSchema() {
		return schema;
	}

	public void setSchema(final String schema) {
		this.schema = schema;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(final String organization) {
		this.organization = organization;
	}
}
