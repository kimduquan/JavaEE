/**
 * 
 */
package epf.portlet.persistence;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.json.JsonValue;
import epf.client.schema.Attribute;
import epf.client.schema.AttributeType;
import epf.client.schema.Embeddable;

/**
 * @author PC
 *
 */
public class AttributeBuilder {
	
	/**
	 * 
	 */
	private transient Attribute attribute;
	/**
	 * 
	 */
	private transient EntityObject object;
	/**
	 * 
	 */
	private transient Map<String, Embeddable> embeddables;

	/**
	 * @return
	 */
	public BasicAttribute build() {
		if(AttributeType.EMBEDDED.equals(attribute.getAttributeType())) {
			final Embeddable embeddable = embeddables.get(attribute.getType());
			final JsonValue value = object.get(attribute.getName());
			final EntityObject embeddedObject = new EntityObject(value != null ? value.asJsonObject() : JsonValue.EMPTY_JSON_OBJECT);
			final List<BasicAttribute> embeddedAttributes = embeddable
					.getAttributes()
					.stream()
					.map(attr -> new AttributeBuilder()
							.setAttribute(attr)
							.setEmbeddables(embeddables)
							.setObject(embeddedObject)
							.build()
							)
					.collect(Collectors.toList());
			return new EmbeddedAttribute(object, attribute, embeddable, embeddedObject, embeddedAttributes);
		}
		return new BasicAttribute(object, attribute);
	}

	public AttributeBuilder setAttribute(final Attribute attribute) {
		this.attribute = attribute;
		return this;
	}

	public AttributeBuilder setObject(final EntityObject object) {
		this.object = object;
		return this;
	}

	public AttributeBuilder setEmbeddables(final Map<String, Embeddable> embeddables) {
		this.embeddables = embeddables;
		return this;
	}
}
