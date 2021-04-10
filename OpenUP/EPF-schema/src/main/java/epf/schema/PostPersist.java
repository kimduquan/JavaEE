/**
 * 
 */
package epf.schema;

/**
 * @author PC
 *
 */
public class PostPersist {

	/**
	 * 
	 */
	private final Object entity;
	
	/**
	 * @param entity
	 */
	public PostPersist(final Object entity) {
		this.entity = entity;
	}

	public Object getEntity() {
		return entity;
	}
}
