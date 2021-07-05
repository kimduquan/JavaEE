/**
 * 
 */
package epf.portlet.persistence;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.json.JsonValue;
import epf.client.schema.Attribute;
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
	 * 
	 */
	private transient Map<String, epf.client.schema.Entity> entities;
	
	/**
	 * @return
	 */
	protected EmbeddedAttribute buidEmbeddedAttribute() {
		final Embeddable embeddable = embeddables.get(attribute.getType());
		final JsonValue value = object.get(attribute.getName());
		final EntityObject embeddedObject = new EntityObject(value != null ? value.asJsonObject() : JsonValue.EMPTY_JSON_OBJECT);
		final List<BasicAttribute> embeddedAttributes = embeddable
				.getAttributes()
				.stream()
				.map(attr -> new AttributeBuilder()
						.setAttribute(attr)
						.setEmbeddables(embeddables)
						.setEntities(entities)
						.setObject(embeddedObject)
						.build()
						)
				.collect(Collectors.toList());
		object.putEmbedded(attribute.getName(), embeddedObject);
		return new EmbeddedAttribute(object, attribute, embeddable, embeddedObject, embeddedAttributes);
	}
	
	/**
	 * @return
	 */
	protected BasicAttribute buildBasicAttribute() {
		return new BasicAttribute(object, attribute);
	}

	/**
	 * @return
	 */
	public BasicAttribute build() {
		if(AttributeUtil.isEmbedded(attribute)) {
			return buidEmbeddedAttribute();
		}
		return buildBasicAttribute();
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

	public AttributeBuilder setEntities(final Map<String, epf.client.schema.Entity> entities) {
		this.entities = entities;
		return this;
	}
}
