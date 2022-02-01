/**
 * 
 */
package epf.portlet.persistence;

import java.io.StringReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.json.JsonValue;

import epf.persistence.schema.client.Attribute;
import epf.persistence.schema.client.AttributeType;
import epf.persistence.schema.client.Entity;

/**
 * @author PC
 *
 */
public class AttributeUtil {
	
	/**
	 * @param attribute
	 * @return
	 */
	public static boolean isBasic(final Attribute attribute) {
		return !attribute.isAssociation() 
				&& !attribute.isCollection() 
				&& AttributeType.BASIC.equals(attribute.getAttributeType());
	}
	
	/**
	 * @return
	 */
	public static boolean isSingular(final Attribute attribute) {
		return AttributeType.MANY_TO_ONE.equals(attribute.getAttributeType())
				|| AttributeType.ONE_TO_ONE.equals(attribute.getAttributeType());
	}
	
	/**
	 * @param attribute
	 * @return
	 */
	public static boolean isPlural(final Attribute attribute) {
		return AttributeType.ONE_TO_MANY.equals(attribute.getAttributeType())
				|| AttributeType.MANY_TO_MANY.equals(attribute.getAttributeType());
	}
	
	/**
	 * @param attribute
	 * @return
	 */
	public static boolean isEmbedded(final Attribute attribute) {
		return AttributeType.EMBEDDED.equals(attribute.getAttributeType());
	}
	
	/**
	 * @param entity
	 * @param attribute
	 * @return
	 */
	public static String getId(final Entity entity, final Attribute attribute) {
		return entity.getType() + "." + attribute.getName();
	}
	
	/**
	 * @param builder
	 * @param attribute
	 */
	static void addDefault(final JsonObjectBuilder builder, final Attribute attribute) {
		if(BigDecimal.class.getName().equals(attribute.getType())) {
			builder.add(attribute.getName(), BigDecimal.ZERO);
		}
		else if(BigInteger.class.getName().equals(attribute.getType())) {
			builder.add(attribute.getName(), BigInteger.ZERO);
		}
		else if(Boolean.class.getName().equals(attribute.getType())) {
			builder.add(attribute.getName(), false);
		}
		else if(Double.class.getName().equals(attribute.getType())) {
			builder.add(attribute.getName(), Double.valueOf(0));
		}
		else if(Integer.class.getName().equals(attribute.getType())) {
			builder.add(attribute.getName(), Integer.valueOf(0));
		}
		else if(Long.class.getName().equals(attribute.getType())) {
			builder.add(attribute.getName(), Long.valueOf(0));
		}
		else if(String.class.getName().equals(attribute.getType())) {
			builder.add(attribute.getName(), "");
		}
		else if(attribute.isCollection()) {
			builder.add(attribute.getName(), JsonValue.EMPTY_JSON_ARRAY);
		}
		else if(attribute.isAssociation()) {
			builder.add(attribute.getName(), JsonValue.EMPTY_JSON_OBJECT);
		}
		else if(AttributeType.BASIC.equals(attribute.getAttributeType())) {
			builder.addNull(attribute.getName());
		}
		else {
			builder.add(attribute.getName(), JsonValue.EMPTY_JSON_OBJECT);
		}
	}
	
	/**
	 * @param object
	 * @param attribute
	 * @param value
	 */
	public static void setValue(final EntityObject object, final Attribute attribute, final String value) {
		if(BigDecimal.class.getName().equals(attribute.getType())) {
			object.put(attribute.getName(), Json.createValue(new BigDecimal(value)));
		}
		else if(BigInteger.class.getName().equals(attribute.getType())) {
			object.put(attribute.getName(), Json.createValue(new BigInteger(value)));
		}
		else if(Boolean.class.getName().equals(attribute.getType())) {
			final boolean b = Boolean.valueOf(value);
			object.put(attribute.getName(), b ? JsonValue.TRUE : JsonValue.FALSE);
		}
		else if(Double.class.getName().equals(attribute.getType())) {
			object.put(attribute.getName(), Json.createValue(Double.valueOf(value)));
		}
		else if(Integer.class.getName().equals(attribute.getType())) {
			object.put(attribute.getName(), Json.createValue(Integer.valueOf(value)));
		}
		else if(Long.class.getName().equals(attribute.getType())) {
			object.put(attribute.getName(), Json.createValue(Long.valueOf(value)));
		}
		else if(String.class.getName().equals(attribute.getType())) {
			object.put(attribute.getName(), Json.createValue(value));
		}
		else if (value != null && !value.isEmpty()){
			try(StringReader reader = new StringReader(value)){
				try(JsonReader jsonReader = Json.createReader(reader)){
					final JsonValue jsonValue = jsonReader.readValue();
					object.put(attribute.getName(), jsonValue);
				}
			}
		}
	}
}
