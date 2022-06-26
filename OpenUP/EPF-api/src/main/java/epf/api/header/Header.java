package epf.api.header;

import java.util.Map;

import epf.api.Extensible;
import epf.api.example.Example;
import epf.api.media.Content;
import epf.api.media.Schema;

/**
 * 
 */
public class Header extends Extensible {

	/**
	 *
	 */
	private String ref;
	
	public enum Style {
		simple
	}
	
	/**
	 *
	 */
	private String description;
	
	/**
	 *
	 */
	private Boolean required;
	
	/**
	 *
	 */
	private Boolean deprecated;
	
	/**
	 *
	 */
	private Boolean allowEmptyValue;
	
	/**
	 *
	 */
	private Style style;
	
	/**
	 *
	 */
	private Boolean explode;
	
	/**
	 *
	 */
	private Schema schema;
	
	/**
	 *
	 */
	private Map<String, Example> examples;
	
	/**
	 *
	 */
	private Object example;
	
	/**
	 *
	 */
	private Content content;

	public String getRef() {
		return ref;
	}

	public void setRef(final String ref) {
		this.ref = ref;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public Boolean getRequired() {
		return required;
	}

	public void setRequired(final Boolean required) {
		this.required = required;
	}

	public Boolean getDeprecated() {
		return deprecated;
	}

	public void setDeprecated(final Boolean deprecated) {
		this.deprecated = deprecated;
	}

	public Boolean getAllowEmptyValue() {
		return allowEmptyValue;
	}

	public void setAllowEmptyValue(final Boolean allowEmptyValue) {
		this.allowEmptyValue = allowEmptyValue;
	}

	public Style getStyle() {
		return style;
	}

	public void setStyle(final Style style) {
		this.style = style;
	}

	public Boolean getExplode() {
		return explode;
	}

	public void setExplode(final Boolean explode) {
		this.explode = explode;
	}

	public Schema getSchema() {
		return schema;
	}

	public void setSchema(final Schema schema) {
		this.schema = schema;
	}

	public Map<String, Example> getExamples() {
		return examples;
	}

	public void setExamples(final Map<String, Example> examples) {
		this.examples = examples;
	}

	public Object getExample() {
		return example;
	}

	public void setExample(final Object example) {
		this.example = example;
	}

	public Content getContent() {
		return content;
	}

	public void setContent(final Content content) {
		this.content = content;
	}
}
