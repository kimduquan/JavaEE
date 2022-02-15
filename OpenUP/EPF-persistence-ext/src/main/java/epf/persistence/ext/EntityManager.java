package epf.persistence.ext;

import java.util.Map;
import java.util.concurrent.CompletionStage;
import javax.persistence.EntityGraph;
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
	 * @param entityClass
	 * @param primaryKey
	 * @param properties
	 * @return
	 */
	<T> CompletionStage<T> find(final Class<T> entityClass, final Object primaryKey, final Map<String, Object> properties);
	
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
	
	/**
	 * @param entity
	 */
	void detach(final Object entity); 
	
	/**
	 * @param <T>
	 * @param rootType
	 * @return
	 */
	<T> EntityGraph<T> createEntityGraph(final Class<T> rootType);
}
