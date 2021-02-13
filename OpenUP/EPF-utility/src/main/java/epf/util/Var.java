package epf.util;

import java.util.function.Consumer;
import java.util.function.Function;

public class Var<T> {

	private T object;
	
	public Var() {
		
	}
	
	public Var(T object) {
		this.object = object;
	}
	
	public T set(T object) {
		this.object = object;
		return object;
	}
	
	public T get() {
		return object;
	}
	
	public void set(Function<T, T> setter) {
		object = setter.apply(object);
	}
	
	public void get(Consumer<T> getter) {
		getter.accept(object);
	}
}
