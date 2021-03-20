package epf.util;

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
	private transient T object;
	
	/**
	 * 
	 */
	public Var() {
		super();
	}
	
	/**
	 * @param object
	 */
	public Var(final T object) {
		this.object = object;
	}
	
	/**
	 * @param object
	 * @return
	 */
	public T set(final T object) {
		this.object = object;
		return object;
	}
	
	/**
	 * @return
	 */
	public T get() {
		return object;
	}
	
	/**
	 * @param setter
	 */
	public void set(final Function<T, T> setter) {
		object = setter.apply(object);
	}
	
	/**
	 * @param getter
	 */
	public void get(final Consumer<T> getter) {
		getter.accept(object);
	}
}
