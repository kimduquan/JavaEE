/**
 * 
 */
package epf.lang.util;

/**
 * @author PC
 *
 */
public abstract class Call<T, R> implements Runnable {
	
	/**
	 * 
	 */
	private transient final T params;
	
	/**
	 * 
	 */
	private transient R result;
	
	/**
	 * @param params
	 */
	public Call(final T params) {
		this.params = params;
	}
	
	/**
	 * @return
	 */
	public T getParams() {
		return params;
	}

	/**
	 * @return the result
	 */
	public R getResult() {
		return result;
	}

	/**
	 * @param result the result to set
	 */
	public void setResult(final R result) {
		this.result = result;
	}
}
