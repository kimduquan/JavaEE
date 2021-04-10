/**
 * 
 */
package epf.schema;

/**
 * @author PC
 *
 */
public class PostUpdate {

	/**
	 * 
	 */
	private final Object entity;
	
	/**
	 * @param entity
	 */
	public PostUpdate(final Object entity) {
		this.entity = entity;
	}

	public Object getEntity() {
		return entity;
	}
}
