package erp.schema.util;

import org.neo4j.ogm.typeconversion.AttributeConverter;
import epf.util.json.ext.JsonUtil;

/**
 * 
 */
public class NameAttributeConverter implements AttributeConverter<String, String> {

	@Override
	public String toGraphProperty(final String value) {
		return JsonUtil.readObject(value).getString("en_US");
	}

	@Override
	public String toEntityAttribute(final String value) {
		return value;
	}

}
