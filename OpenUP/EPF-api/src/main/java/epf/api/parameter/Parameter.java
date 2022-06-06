package epf.api.parameter;

import java.util.Map;

import epf.api.Extensible;
import epf.api.example.Example;
import epf.api.media.Content;
import epf.api.media.Schema;

/**
 * 
 */
public class Parameter extends Extensible {

	private String ref;
	
	enum Style {
		matrix,
		label,
		form,
		simple,
		spaceDelimited,
		pipeDelimited,
		deepObject
	};
	
	enum In {
		path,
		query,
		header,
		cookie
	};
	
	private String name;
	
	private In in;
	
	private String description;
	
	private Boolean required;
	
	private Boolean deprecated;
	
	private Boolean allowEmptyValue;
	
	private Parameter.Style style;
	
	private Boolean explode;
	
	private Boolean allowReserved;
	
	private Schema schema;
	
	private Map<String, Example> examples;
	
	private Object example;
	
	private Content content;

	public String getRef() {
		return ref;
	}

	public void setRef(String ref) {
		this.ref = ref;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public In getIn() {
		return in;
	}

	public void setIn(In in) {
		this.in = in;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getRequired() {
		return required;
	}

	public void setRequired(Boolean required) {
		this.required = required;
	}

	public Boolean getDeprecated() {
		return deprecated;
	}

	public void setDeprecated(Boolean deprecated) {
		this.deprecated = deprecated;
	}

	public Boolean getAllowEmptyValue() {
		return allowEmptyValue;
	}

	public void setAllowEmptyValue(Boolean allowEmptyValue) {
		this.allowEmptyValue = allowEmptyValue;
	}

	public Parameter.Style getStyle() {
		return style;
	}

	public void setStyle(Parameter.Style style) {
		this.style = style;
	}

	public Boolean getExplode() {
		return explode;
	}

	public void setExplode(Boolean explode) {
		this.explode = explode;
	}

	public Boolean getAllowReserved() {
		return allowReserved;
	}

	public void setAllowReserved(Boolean allowReserved) {
		this.allowReserved = allowReserved;
	}

	public Schema getSchema() {
		return schema;
	}

	public void setSchema(Schema schema) {
		this.schema = schema;
	}

	public Map<String, Example> getExamples() {
		return examples;
	}

	public void setExamples(Map<String, Example> examples) {
		this.examples = examples;
	}

	public Object getExample() {
		return example;
	}

	public void setExample(Object example) {
		this.example = example;
	}

	public Content getContent() {
		return content;
	}

	public void setContent(Content content) {
		this.content = content;
	}
}
