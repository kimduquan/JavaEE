/**
 * 
 */
package epf.portlet.persistence;

import java.io.StringReader;
import java.util.List;
import javax.json.Json;
import javax.json.JsonReader;
import epf.client.schema.Attribute;
import epf.client.schema.Embeddable;

/**
 * @author PC
 *
 */
public class EmbeddedAttribute extends EntityAttribute {
	
	/**
	 * 
	 */
	private final Embeddable embeddable;
	
	/**
	 * 
	 */
	private final EntityObject embeddedObject;
	
	/**
	 * 
	 */
	private List<EntityAttribute> attributes;

	/**
	 * @param object
	 * @param attribute
	 * @param embeddable
	 */
	protected EmbeddedAttribute(final EntityObject object, final Attribute attribute, final Embeddable embeddable, final EntityObject embeddedObject, final List<EntityAttribute> attributes) {
		super(object, attribute);
		this.embeddable = embeddable;
		this.embeddedObject = embeddedObject;
		this.attributes = attributes;
	}

	public Embeddable getEmbeddable() {
		return embeddable;
	}

	public List<EntityAttribute> getAttributes() {
		return attributes;
	}

	public EntityObject getEmbeddedObject() {
		return embeddedObject;
	}
	
	/**
	 * @return
	 */
	@Override
	public String getValue() {
		return AttributeUtil.getAsString(getEmbeddedObject().get(getAttribute().getName()));
	}
	
	/**
	 * @param value
	 */
	@Override
	public void setValue(final String value) {
		try(StringReader reader = new StringReader(value)){
			try(JsonReader json = Json.createReader(reader)){
				getEmbeddedObject().put(getAttribute().getName(), json.readValue());
			}
		}
	}
}
