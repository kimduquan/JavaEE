package epf.json.schema;

public class JsonString extends TypeValue {

	private static final long serialVersionUID = 1L;
	
	private Integer minLength;
	private Integer maxLength;
	private String pattern;
	private Format format;
	
	public JsonString() {
		setType(Type.string);
	}
	
	public Integer getMinLength() {
		return minLength;
	}
	public void setMinLength(final Integer minLength) {
		this.minLength = minLength;
	}
	public Integer getMaxLength() {
		return maxLength;
	}
	public void setMaxLength(final Integer maxLength) {
		this.maxLength = maxLength;
	}
	public String getPattern() {
		return pattern;
	}
	public void setPattern(final String pattern) {
		this.pattern = pattern;
	}
	public Format getFormat() {
		return format;
	}
	public void setFormat(final Format format) {
		this.format = format;
	}
}
