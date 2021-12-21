package epf.persistence.internal.object;

import java.util.function.Supplier;

/**
 * @author PC
 *
 */
public interface ObjectManagerFactory extends Supplier<ObjectManager> {

	/**
	 * @return
	 */
	ObjectManager createObjectManager();
	
	/**
	 *
	 */
	default ObjectManager get() {
		return createObjectManager();
	}
}
