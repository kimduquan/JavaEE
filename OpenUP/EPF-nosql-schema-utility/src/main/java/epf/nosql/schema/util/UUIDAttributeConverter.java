package epf.nosql.schema.util;

import java.util.UUID;
import jakarta.nosql.AttributeConverter;

public class UUIDAttributeConverter implements AttributeConverter<String, UUID> {

	@Override
	public UUID convertToDatabaseColumn(final String attribute) {
		return UUID.fromString(attribute);
	}

	@Override
	public String convertToEntityAttribute(final UUID dbData) {
		return dbData.toString();
	}

}
