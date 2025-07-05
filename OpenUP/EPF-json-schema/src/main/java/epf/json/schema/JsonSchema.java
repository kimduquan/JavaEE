package epf.json.schema;

public class JsonSchema extends JsonObject {
	
	private static final long serialVersionUID = 1L;
	
	private String schema;
	private String id;
	private String title;
	private String description;
	private Object[] examples;
	private Boolean readOnly;
	private Boolean writeOnly;
	private Boolean deprecated;
	
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
	public Boolean isReadOnly() {
		return readOnly;
	}
	public void setReadOnly(final Boolean readOnly) {
		this.readOnly = readOnly;
	}
	public Boolean isWriteOnly() {
		return writeOnly;
	}
	public void setWriteOnly(final Boolean writeOnly) {
		this.writeOnly = writeOnly;
	}
	public Boolean isDeprecated() {
		return deprecated;
	}
	public void setDeprecated(final Boolean deprecated) {
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
