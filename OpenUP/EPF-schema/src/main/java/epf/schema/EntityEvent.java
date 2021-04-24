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

	public Object getEntity() {
		return entity;
	}

	public void setEntity(final Object entity) {
		this.entity = entity;
	}
}
