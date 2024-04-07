package epf.math;

import java.math.BigInteger;

/**
 * @param <T>
 */
public class MathObject<T extends Object> {

	/**
	 * 
	 */
	private final T object;
	
	/**
	 * 
	 */
	private final Long key;
	
	/**
	 * 
	 */
	private final BigInteger value;
	
	/**
	 * @param object
	 * @param key
	 */
	public MathObject(T object, Long key) {
		this.object = object;
		this.key = key;
		this.value = BigInteger.valueOf(key);
	}
	
	/**
	 * @param object
	 * @param key
	 * @param value
	 */
	public MathObject(T object, Long key, BigInteger value) {
		this.object = object;
		this.key = key;
		this.value = value;
	}
	
	@Override
	public String toString() {
		return String.valueOf(object);
	}

	public T getObject() {
		return object;
	}

	public Long getKey() {
		return key;
	}

	public BigInteger getValue() {
		return value;
	}
}
