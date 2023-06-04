package epf.workflow.event.mapping;

import java.util.UUID;
import org.eclipse.jnosql.mapping.AttributeConverter;

/**
 * @author PC
 *
 */
public class UUIDAttributeConverter implements AttributeConverter<String, UUID> {

	@Override
	public UUID convertToDatabaseColumn(final String attribute) {
		return UUID.fromString(attribute);
	}

	@Override
	public String convertToEntityAttribute(final UUID dbData) {
		// TODO Auto-generated method stub
		return dbData.toString();
	}

}
