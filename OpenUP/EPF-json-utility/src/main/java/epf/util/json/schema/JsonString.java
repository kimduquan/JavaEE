package epf.util.json.schema;

/**
 * @author PC
 *
 */
public class JsonString extends TypeValue {

	/**
	 * 
	 */
	private int minLength;
	/**
	 * 
	 */
	private int maxLength;
	/**
	 * 
	 */
	private String pattern;
	/**
	 * 
	 */
	private Format format;
	
	public int getMinLength() {
		return minLength;
	}
	public void setMinLength(final int minLength) {
		this.minLength = minLength;
	}
	public int getMaxLength() {
		return maxLength;
	}
	public void setMaxLength(final int maxLength) {
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
