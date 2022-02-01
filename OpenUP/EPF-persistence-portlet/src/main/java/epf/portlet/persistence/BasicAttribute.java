/**
 * 
 */
package epf.portlet.persistence;

import java.io.StringReader;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonReader;
import javax.json.JsonValue;

import epf.persistence.schema.client.Attribute;
import epf.portlet.util.json.JsonUtil;

/**
 * @author PC
 *
 */
public class BasicAttribute {

	/**
	 * 
	 */
	private final Attribute attribute;
	/**
	 * 
	 */
	private final EntityObject object;
	
	/**
	 * @param object
	 * @param attribute
	 */
	protected BasicAttribute(final EntityObject object, final Attribute attribute) {
		this.attribute = attribute;
		this.object = object;
	}
	
	/**
	 * @return
	 */
	public String getValue() {
		return JsonUtil.toString(object.get(attribute.getName()));
	}
	
	/**
	 * @param value
	 */
	public void setValue(final String value) {
		AttributeUtil.setValue(object, attribute, value);
	}
	
	/**
	 * @return
	 */
	public String[] getValues() {
		final JsonValue value = object.get(attribute.getName());
		if(value != null) {
			final JsonArray array = value.asJsonArray();
			return array
					.stream()
					.map(JsonValue::toString)
					.collect(Collectors.toList())
					.toArray(new String[0]);
		}
		return null;
	}
	
	/**
	 * @param value
	 */
	public void setValues(final String[] values) {
		final JsonArrayBuilder builder = Json.createArrayBuilder();
		Stream.of(values).forEach(value -> {
			try(StringReader reader = new StringReader(value)){
				try(JsonReader json = Json.createReader(reader)){
					builder.add(json.readValue());
				}
			}
		});
		object.put(attribute.getName(), builder.build());
	}

	public Attribute getAttribute() {
		return attribute;
	}
	
	public boolean isBasic() {
		return AttributeUtil.isBasic(attribute);
	}
	
	public boolean isEmbedded() {
		return AttributeUtil.isEmbedded(attribute);
	}
	
	public boolean isSingular() {
		return AttributeUtil.isSingular(attribute);
	}
	
	public boolean isPlural() {
		return AttributeUtil.isPlural(attribute);
	}
}
