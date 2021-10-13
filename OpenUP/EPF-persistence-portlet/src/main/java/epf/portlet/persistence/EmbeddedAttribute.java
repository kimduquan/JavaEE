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
import epf.portlet.util.JsonUtil;

/**
 * @author PC
 *
 */
public class EmbeddedAttribute extends BasicAttribute {
	
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
	private List<BasicAttribute> attributes;

	/**
	 * @param object
	 * @param attribute
	 * @param embeddable
	 */
	protected EmbeddedAttribute(final EntityObject object, final Attribute attribute, final Embeddable embeddable, final EntityObject embeddedObject, final List<BasicAttribute> attributes) {
		super(object, attribute);
		this.embeddable = embeddable;
		this.embeddedObject = embeddedObject;
		this.attributes = attributes;
	}

	public Embeddable getEmbeddable() {
		return embeddable;
	}

	public List<BasicAttribute> getAttributes() {
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
		return JsonUtil.toString(getEmbeddedObject().get(getAttribute().getName()));
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
