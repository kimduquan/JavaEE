/**
 * 
 */
package epf.client.portlet.persistence;

/**
 * @author PC
 *
 */
public interface PersistenceView {

	/**
	 * @param entity
	 * @return
	 */
	String merge(final Object entity);
	/**
	 * @param entity
	 * @return
	 */
	void remove(final Object entity) throws Exception;
	
	/**
	 * @param entity
	 * @return
	 */
	int indexOf(final Object entity);
}
