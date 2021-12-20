package epf.persistence.object;

/**
 * @author PC
 *
 */
public interface ObjectPersistence {
	
	/**
	 * @param name
	 * @return
	 */
	ObjectManagerFactory createObjectManagerFactory(final String name, final Object properties);
}
