/**
 * 
 */
package epf.portlet.persistence;

import java.math.BigDecimal;
import java.math.BigInteger;
import javax.json.JsonObjectBuilder;
import javax.json.JsonString;
import javax.json.JsonValue;
import epf.client.schema.Attribute;
import epf.client.schema.AttributeType;
import epf.client.schema.Entity;

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
				&& attribute.getAttributeType().equals(AttributeType.BASIC);
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
	}
	
	/**
	 * @param value
	 * @return
	 */
	public static String getAsString(final JsonValue value) {
		String string;
		if(value instanceof JsonString) {
			string = ((JsonString)value).getString();
		}
		else {
			string = value.toString();
		}
		return string;
	}
}
