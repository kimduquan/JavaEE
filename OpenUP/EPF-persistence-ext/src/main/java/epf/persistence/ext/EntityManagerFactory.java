package epf.persistence.ext;

import java.io.Closeable;
import java.util.Map;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.metamodel.Metamodel;

/**
 * @author PC
 *
 */
public interface EntityManagerFactory extends Closeable {
	
	/**
	 * @param props
	 * @return
	 */
	EntityManager createEntityManager(final Map<String, Object> props);
	
	/**
	 * @return
	 */
	CriteriaBuilder getCriteriaBuilder();
    
    /**
     * @return
     */
    Metamodel getMetamodel();
}
