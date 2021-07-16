/**
 * 
 */
package epf.client.portlet.persistence;

/**
 * @author PC
 *
 */
public interface QueryView {

	/**
	 * @return
	 */
	int getFirstResult();
	/**
	 * @param firstResult
	 */
	void setFirstResult(final int firstResult);
	/**
	 * @return
	 */
	int getMaxResults();
	/**
	 * @param maxResults
	 */
	void setMaxResults(final int maxResults);
	/**
	 * @throws Exception
	 */
	void executeQuery() throws Exception;
	
	/**
	 * @param object
	 * @return
	 */
	int getIndexOf(final String attribute, final Object object);
	
	/**
	 * @return
	 */
	int getResultSize();
	
	/**
	 * @param attribute
	 * @param value
	 * @return
	 */
	String merge(final String attribute, final Object value);
}
