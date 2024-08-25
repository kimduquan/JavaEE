package epf.math;

import java.math.BigDecimal;
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
	 * 
	 */
	private final BigDecimal entropy;
	
	/**
	 * @param object
	 * @param key
	 */
	public MathObject(T object, Long key) {
		this.object = object;
		this.key = key;
		this.value = BigInteger.valueOf(key);
		entropy = BigDecimal.ZERO;
	}
	
	/**
	 * @param object
	 * @param key
	 * @param value
	 * @param entropy
	 */
	public MathObject(final T object, final Long key, final BigInteger value, final BigDecimal entropy) {
		this.object = object;
		this.key = key;
		this.value = value;
		this.entropy = entropy;
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

	public BigDecimal getEntropy() {
		return entropy;
	}
}
