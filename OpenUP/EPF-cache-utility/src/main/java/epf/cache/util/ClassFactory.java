package epf.cache.util;

import java.util.Objects;
import javax.cache.configuration.Factory;

/**
 * @author PC
 *
 * @param <T>
 */
public class ClassFactory<T> implements Factory<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	private transient final Class<T> cls;
	
	/**
	 * @param cls
	 */
	public ClassFactory(final Class<T> cls) {
		Objects.requireNonNull(cls, "Class");
		this.cls = cls;
	}

	@Override
	public T create() {
		try {
			return cls.getConstructor().newInstance();
		} 
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
