package epf.json.schema;

/**
 * @author PC
 *
 */
public class JsonSchema extends JsonObject {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private String schema;
	/**
	 * 
	 */
	private String id;
	/**
	 * 
	 */
	private String title;
	/**
	 * 
	 */
	private String description;
	/**
	 * 
	 */
	private Object[] examples;
	/**
	 * 
	 */
	private boolean readOnly;
	/**
	 * 
	 */
	private boolean writeOnly;
	/**
	 * 
	 */
	private boolean deprecated;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(final String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(final String description) {
		this.description = description;
	}
	public Object[] getExamples() {
		return examples;
	}
	public void setExamples(final Object[] examples) {
		this.examples = examples;
	}
	public boolean isReadOnly() {
		return readOnly;
	}
	public void setReadOnly(final boolean readOnly) {
		this.readOnly = readOnly;
	}
	public boolean isWriteOnly() {
		return writeOnly;
	}
	public void setWriteOnly(final boolean writeOnly) {
		this.writeOnly = writeOnly;
	}
	public boolean isDeprecated() {
		return deprecated;
	}
	public void setDeprecated(final boolean deprecated) {
		this.deprecated = deprecated;
	}
	public String getSchema() {
		return schema;
	}
	public void setSchema(final String schema) {
		this.schema = schema;
	}
	public String getId() {
		return id;
	}
	public void setId(final String id) {
		this.id = id;
	}
}
