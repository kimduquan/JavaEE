package epf.persistence.ext;

import java.util.concurrent.CompletionStage;
import java.util.function.Function;
import javax.persistence.criteria.CriteriaQuery;

/**
 * @author PC
 *
 */
public interface EntityManager extends epf.util.AutoCloseable {
	
	/**
	 * 
	 */
	String FETCH_GRAPH = "javax.persistence.fetchgraph";

	/**
	 * @param entity
	 * @return
	 */
	<T> CompletionStage<Void> persist(final T entity);
	
	/**
	 * @param <T>
	 * @param entity
	 * @param function
	 * @return
	 */
	<T> CompletionStage<T> merge(final T entity, final Function<T, T> function);
	
	/**
	 * @param <T>
	 * @param entity
	 * @return
	 */
	<T> CompletionStage<Void> remove(final T entity);
	
	/**
	 * @param <T>
	 * @param <R>
	 * @param entityClass
	 * @param primaryKey
	 * @param function
	 * @return
	 */
	<T, R> CompletionStage<R> find(
			final Class<T> entityClass, 
			final Object primaryKey,
			final Function<T, R> function);
	
	/**
	 * @param <R>
	 * @param <T>
	 * @param criteriaQuery
	 * @param function
	 * @return
	 */
	<R, T> CompletionStage<T> createQuery(final CriteriaQuery<R> criteriaQuery, final Function<Query<R>, CompletionStage<T>> function); 
	
	/**
	 * @param <R>
	 * @param <T>
	 * @param sqlString
	 * @param function
	 * @return
	 */
	<R, T> CompletionStage<T> createNativeQuery(final String sqlString, final Function<Query<R>, CompletionStage<T>> function);
	
	/**
	 * 
	 */
	void joinTransaction();
	
	/**
	 * @return
	 */
	boolean isJoinedToTransaction();
}
