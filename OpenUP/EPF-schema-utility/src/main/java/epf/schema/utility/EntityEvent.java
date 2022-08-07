package epf.schema.utility;

import java.io.Serializable;
import javax.json.bind.annotation.JsonbTypeAdapter;
import epf.util.json.Adapter;

/**
 * @author PC
 *
 */
public class EntityEvent implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	private Long time;
	
	/**
	 *
	 */
	private String tenant;
	
	/**
	 * 
	 */
	private String schema;
	
	/**
	 * 
	 */
	@JsonbTypeAdapter(Adapter.class)
	private Object entity;
	
	@Override
	public String toString() {
		return String.format("[%d]%s/%s", time, getClass().getName(), entity);
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

	public String getTenant() {
		return tenant;
	}

	public void setTenant(final String tenant) {
		this.tenant = tenant;
	}

	public String getSchema() {
		return schema;
	}

	public void setSchema(final String schema) {
		this.schema = schema;
	}
}
