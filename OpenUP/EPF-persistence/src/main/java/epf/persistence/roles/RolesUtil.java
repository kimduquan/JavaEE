/**
 * 
 */
package epf.persistence.roles;

import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import openup.schema.Naming;
import openup.schema.roles.Role;

/**
 * @author PC
 *
 */
public class RolesUtil {

	/**
     * @param manager
     * @return
     */
    public static Set<String> getRoleNames(final Role role){
    	return role.getRoles()
    			.stream()
    			.map(r -> r.getName())
    			.collect(Collectors.toSet());
    }
    
    /**
     * @param manager
     * @param name
     * @return
     */
    public static Role getRole(final EntityManager manager, final String name) {
    	return manager.createNamedQuery(Naming.FIND_ROLES_BY_NAME, Role.class)
    			.setParameter("name", name)
    			.getSingleResult();
    }
}
