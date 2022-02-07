package epf.persistence.ext;

import java.io.Closeable;
import java.util.Map;
import java.util.concurrent.CompletionStage;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.metamodel.Metamodel;

/**
 * @author PC
 *
 */
public interface EntityManagerFactory extends Closeable {

	/**
	 * @return
	 */
	CompletionStage<EntityManager> createEntityManager(final Map<String, Object> props);
	
	/**
	 * @return
	 */
	CriteriaBuilder getCriteriaBuilder();
    
    /**
     * @return
     */
    Metamodel getMetamodel();
}
