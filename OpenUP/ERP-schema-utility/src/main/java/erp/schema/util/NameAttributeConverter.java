package erp.schema.util;

import org.neo4j.ogm.typeconversion.AttributeConverter;

/**
 * 
 */
public class NameAttributeConverter implements AttributeConverter<String, String> {

	@Override
	public String toGraphProperty(final String value) {
		// TODO Auto-generated method stub
		return value;
	}

	@Override
	public String toEntityAttribute(final String value) {
		// TODO Auto-generated method stub
		return value;
	}

}
