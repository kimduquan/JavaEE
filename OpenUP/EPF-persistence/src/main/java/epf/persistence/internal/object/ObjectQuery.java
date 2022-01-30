package epf.persistence.internal.object;

import java.util.List;
import java.util.concurrent.Callable;

/**
 * @author PC
 *
 * @param <X>
 */
public interface ObjectQuery<T> extends Callable<T> {

	/**
	 * @return
	 */
	List<T> getResultList();
	
	/**
	 * @return
	 */
	T getSingleResult();
	
	/**
	 * @param maxResult
	 * @return
	 */
	ObjectQuery<T> setMaxResults(final int maxResult);

    /**
     * @param startPosition
     * @return
     */
    ObjectQuery<T> setFirstResult(final int startPosition);
    
    /**
     * @param name
     * @param value
     * @return
     */
    ObjectQuery<T> setParameter(final String name, final Object value);
    
    /**
     * @param position
     * @param value
     * @return
     */
    ObjectQuery<T> setParameter(final int position, final Object value);
    
    /**
     * @return
     */
    int executeUpdate();
    
    /**
     *
     */
    default T call() throws Exception {
    	executeUpdate();
    	return getSingleResult();
    }
}
