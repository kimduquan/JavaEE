package epf.lang.messaging.messenger;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import epf.util.logging.LogManager;
import jakarta.persistence.metamodel.Attribute;
import jakarta.persistence.metamodel.EntityType;

public class EntityAttributeGetter {
	
	private static final Logger LOGGER = LogManager.getLogger(EntityAttributeGetter.class.getName());

	public Object get(final Object entity, final String attribute, final Map<String, EntityType<?>> entityTypes, final Map<String, List<String>> missingAttributes) throws IOException {
		String entityTypeName = "";
		String attributeName = attribute;
		if(entity != null) {
			final EntityType<?> entityType = entityTypes.get(entity.getClass().getName());
			if(entityType != null) {
				entityTypeName = entityType.getName();
				final int dotIndex = attribute.indexOf('.');
				String subAttribute = null;
				if(dotIndex > -1) {
					attributeName = attribute.substring(0, dotIndex);
					subAttribute = attribute.substring(dotIndex + 1);
				}
				for(Attribute<?, ?> attr : entityType.getAttributes()) {
					if(attr.getName().equals(attributeName)) {
						Object value = null;
						if(attr.getJavaMember() instanceof Field) {
							final Field field = (Field) attr.getJavaMember();
							try {
								value = field.get(entity);
								if(value != null && subAttribute != null) {
									return get(value, subAttribute, entityTypes, missingAttributes);
								}
							} 
							catch (Exception e) {
								LOGGER.log(Level.SEVERE, attributeName, e);
							}
						}
						return value;
					}
				}
			}
		}
		final List<String> attributes = missingAttributes.computeIfAbsent(entityTypeName, name -> new ArrayList<>());
		if(!attributes.contains(attributeName)) {
			attributes.add(attributeName);
		}
		return null;
	}
}
