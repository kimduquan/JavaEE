/**
 * 
 */
package epf.schema;

import java.io.Serializable;

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
	private Object entity;
	
	@Override
	public String toString() {
		return String.format("%s/%s", getClass().getName(), entity);
	}

	public Object getEntity() {
		return entity;
	}

	public void setEntity(final Object entity) {
		this.entity = entity;
	}
}
