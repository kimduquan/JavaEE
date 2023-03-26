package epf.json.schema;

/**
 * @author PC
 *
 */
public class JsonNumber extends TypeValue {

	/**
	 * 
	 */
	private int multipleOf;
	/**
	 * 
	 */
	private int minimum;
	/**
	 * 
	 */
	private int maximum;
	/**
	 * 
	 */
	private int exclusiveMinimum;
	/**
	 * 
	 */
	private int exclusiveMaximum;
	
	public int getMultipleOf() {
		return multipleOf;
	}
	public void setMultipleOf(int multipleOf) {
		this.multipleOf = multipleOf;
	}
	public int getMinimum() {
		return minimum;
	}
	public void setMinimum(int minimum) {
		this.minimum = minimum;
	}
	public int getMaximum() {
		return maximum;
	}
	public void setMaximum(int maximum) {
		this.maximum = maximum;
	}
	public int getExclusiveMinimum() {
		return exclusiveMinimum;
	}
	public void setExclusiveMinimum(int exclusiveMinimum) {
		this.exclusiveMinimum = exclusiveMinimum;
	}
	public int getExclusiveMaximum() {
		return exclusiveMaximum;
	}
	public void setExclusiveMaximum(int exclusiveMaximum) {
		this.exclusiveMaximum = exclusiveMaximum;
	}
}
