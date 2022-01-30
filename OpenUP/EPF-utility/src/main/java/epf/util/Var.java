package epf.util;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author PC
 *
 * @param <T>
 */
public class Var<T> {

	/**
	 * 
	 */
	private transient Optional<T> object;
	
	/**
	 * 
	 */
	public Var() {
		object = Optional.empty();
	}
	
	/**
	 * @param object
	 */
	public Var(final T object) {
		this.object = Optional.ofNullable(object);
	}
	
	/**
	 * @param object
	 * @return
	 */
	public T set(final T object) {
		this.object = Optional.ofNullable(object);
		return object;
	}
	
	/**
	 * @return
	 */
	public Optional<T> get() {
		return object;
	}
	
	/**
	 * @param setter
	 */
	public void set(final Function<T, T> setter) {
		final T newObject = setter.apply(object.orElse(null));
		object = Optional.ofNullable(newObject);
	}
	
	/**
	 * @param getter
	 */
	public void get(final Consumer<T> getter) {
		getter.accept(object.orElse(null));
	}
}
