package epf.persistence.util;

import java.util.concurrent.CompletionStage;
import javax.persistence.criteria.CriteriaQuery;
import epf.util.io.Closeable;

/**
 * @author PC
 *
 */
public interface EntityManager extends Closeable {

	/**
	 * @param entity
	 * @return
	 */
	<T> CompletionStage<Void> persist(final T entity);
	
	/**
	 * @param <T>
	 * @param entity
	 * @return
	 */
	<T> CompletionStage<T> merge(final T entity);
	
	/**
	 * @param <T>
	 * @param entity
	 * @return
	 */
	<T> CompletionStage<Void> remove(final T entity);
	
	/**
	 * @param <T>
	 * @param entityClass
	 * @param primaryKey
	 * @return
	 */
	<T> CompletionStage<T> find(final Class<T> entityClass, final Object primaryKey);
	
	/**
	 * @param <T>
	 * @param entity
	 * @return
	 */
	<T> CompletionStage<Void> refresh(final T entity);
	
	/**
	 * @param <T>
	 * @param criteriaQuery
	 * @return
	 */
	<R> Query<R> createQuery(final CriteriaQuery<R> criteriaQuery); 
	
	/**
	 * @param sqlString
	 * @return
	 */
	<R> Query<R> createNativeQuery(final String sqlString);
}
