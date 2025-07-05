package epf.json.schema;

public class JsonNumber extends TypeValue {

	private static final long serialVersionUID = 1L;
	
	private Integer multipleOf;
	private Integer minimum;
	private Integer maximum;
	private Integer exclusiveMinimum;
	private Integer exclusiveMaximum;
	
	public JsonNumber() {
		setType(Type.number);
	}
	
	public Integer getMultipleOf() {
		return multipleOf;
	}
	public void setMultipleOf(Integer multipleOf) {
		this.multipleOf = multipleOf;
	}
	public Integer getMinimum() {
		return minimum;
	}
	public void setMinimum(Integer minimum) {
		this.minimum = minimum;
	}
	public Integer getMaximum() {
		return maximum;
	}
	public void setMaximum(Integer maximum) {
		this.maximum = maximum;
	}
	public Integer getExclusiveMinimum() {
		return exclusiveMinimum;
	}
	public void setExclusiveMinimum(Integer exclusiveMinimum) {
		this.exclusiveMinimum = exclusiveMinimum;
	}
	public Integer getExclusiveMaximum() {
		return exclusiveMaximum;
	}
	public void setExclusiveMaximum(Integer exclusiveMaximum) {
		this.exclusiveMaximum = exclusiveMaximum;
	}
}
