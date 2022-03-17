package epf.persistence.ext;

import java.util.concurrent.CompletionStage;
import java.util.function.Function;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.metamodel.EntityType;

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
	<T> CompletionStage<T> merge(final EntityType<?> entityType, final Object primaryKey, final T entity);
	
	/**
	 * @param <T>
	 * @param cls
	 * @param primaryKey
	 * @return
	 */
	<T> CompletionStage<Void> remove(final Class<T> cls, final Object primaryKey);
	
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
