package epf.transaction.api.media;

import java.util.Map;
import epf.transaction.api.Extensible;
import epf.transaction.api.example.Example;

/**
 * 
 */
public class MediaType extends Extensible {

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
	private Map<String, Encoding> encoding;

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

	public Map<String, Encoding> getEncoding() {
		return encoding;
	}

	public void setEncoding(final Map<String, Encoding> encoding) {
		this.encoding = encoding;
	}
}
