/**
 * 
 */
package epf.schema;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;

/**
 * @author PC
 *
 */
public class PostRemove {

	/**
	 * 
	 */
	private Object entity;
	
	/**
	 * @param entity
	 */
	@JsonbCreator
	public PostRemove(@JsonbProperty("entity") final Object entity) {
		this.entity = entity;
	}

	public Object getEntity() {
		return entity;
	}
}
