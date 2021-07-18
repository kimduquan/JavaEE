/**
 * 
 */
package epf.client.portlet.persistence;

import java.util.List;

/**
 * @author PC
 *
 */
public interface ResultList {

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
	 * @return
	 */
	List<?> getResultList();
}
