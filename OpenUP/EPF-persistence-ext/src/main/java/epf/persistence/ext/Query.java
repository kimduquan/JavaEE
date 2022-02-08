package epf.persistence.ext;

import java.util.List;
import java.util.concurrent.CompletionStage;

/**
 * @author PC
 *
 */
public interface Query<R> {

	/**
	 * @param parameter
	 * @param argument
	 * @return
	 */
	Query<R> setParameter(final int parameter, final Object argument);

	/**
	 * @param parameter
	 * @param argument
	 * @return
	 */
	Query<R> setParameter(final String parameter, final Object argument);
	
	/**
	 * @param maxResults
	 * @return
	 */
	Query<R> setMaxResults(final int maxResults);

	/**
	 * @param firstResult
	 * @return
	 */
	Query<R> setFirstResult(final int firstResult);
	
	/**
	 * @return
	 */
	CompletionStage<R> getSingleResult();
	
	/**
	 * @return
	 */
	CompletionStage<List<R>> getResultList();
	
	/**
	 * @return
	 */
	CompletionStage<Integer> executeUpdate();
}
