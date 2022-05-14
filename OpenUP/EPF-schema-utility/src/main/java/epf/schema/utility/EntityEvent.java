package epf.schema.utility;

import java.io.Serializable;
import java.util.Map;
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
	private long time; 
	
	/**
	 *
	 */
	private Map<String, Object> metadata;
	
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

	public long getTime() {
		return time;
	}

	public void setTime(final long time) {
		this.time = time;
	}

	public Map<String, Object> getMetadata() {
		return metadata;
	}

	public void setMetadata(final Map<String, Object> metadata) {
		this.metadata = metadata;
	}
}
