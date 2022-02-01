package epf.persistence.reactive;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.metamodel.Metamodel;
import io.smallrye.mutiny.Uni;

/**
 * @author PC
 *
 */
public interface RxEntityManager {

	/**
	 * @param entity
	 */
	Uni<Void> persist(final Object entity);
	
	/**
	 * @param <T>
	 * @param entity
	 * @return
	 */
	<T> Uni<T> merge(final T entity);
	
	/**
	 * @param entity
	 */
	Uni<Void> remove(final Object entity);
	
	/**
	 * @param <T>
	 * @param entityClass
	 * @param primaryKey
	 * @return
	 */
	<T> Uni<T> find(final Class<T> entityClass, final Object primaryKey);
	
	/**
	 * 
	 */
	Uni<Void> flush();
	
	/**
	 * @param entity
	 */
	Uni<Void> refresh(final Object entity);
	
	/**
	 * 
	 */
	Uni<Void> close();
	
	/**
	 * @return
	 */
	CriteriaBuilder getCriteriaBuilder();
	
	/**
	 * @return
	 */
	Metamodel getMetamodel();
}
