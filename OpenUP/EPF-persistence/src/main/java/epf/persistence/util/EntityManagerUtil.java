package epf.persistence.util;

import java.util.Objects;
import javax.persistence.EntityManager;

/**
 * @author PC
 *
 */
public interface EntityManagerUtil {

	/**
	 * @param manager
	 * @return
	 */
	static EntityManager joinTransaction(final EntityManager manager){
    	Objects.requireNonNull(manager, "EntityManager");
        if(!manager.isJoinedToTransaction()){
        	manager.joinTransaction();
        }
        return manager;
    }
}
