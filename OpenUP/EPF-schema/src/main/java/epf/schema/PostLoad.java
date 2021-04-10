/**
 * 
 */
package epf.schema;

/**
 * @author PC
 *
 */
public class PostLoad {

	/**
	 * 
	 */
	private final Object entity;
	
	/**
	 * @param entity
	 */
	public PostLoad(final Object entity) {
		this.entity = entity;
	}

	public Object getEntity() {
		return entity;
	}
}
