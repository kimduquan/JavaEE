package epf.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @author PC
 *
 */
public interface ClassUtil {
	
	/**
	 * @param <T>
	 * @param classes
	 * @return
	 * @throws Exception
	 */
	static <T> List<T> newInstances(final List<Class<? extends T>> classes) throws Exception {
		final List<T> instances = new ArrayList<>();
		for(Class<? extends T> clazz : classes) {
			instances.add(clazz.getConstructor().newInstance());
		}
		return instances;
	}
}
