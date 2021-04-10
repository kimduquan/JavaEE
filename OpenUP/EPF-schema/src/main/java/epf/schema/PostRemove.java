/**
 * 
 */
package epf.schema;

/**
 * @author PC
 *
 */
public class PostRemove {

	/**
	 * 
	 */
	private final Object entity;
	
	/**
	 * @param entity
	 */
	public PostRemove(final Object entity) {
		this.entity = entity;
	}

	public Object getEntity() {
		return entity;
	}
}
