package epf.persistence.util;

import java.lang.reflect.Field;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;

public interface EntityUtil {
	
	static Object convertEntityId(final EntityType<?> entityType, final Object id) {
		final String entityIdFieldType = entityType.getIdType().getJavaType().getName();
    	Object entityId = id;
    	switch(entityIdFieldType) {
    		case "java.lang.Integer":
    			entityId = Integer.valueOf(String.valueOf(entityId));
    			break;
    		case "java.lang.Long":
    			entityId = Long.valueOf(String.valueOf(entityId));
    			break;
    		default:
    			break;
		}
    	return entityId;
    }
	
	static Object getEntityId(final EntityType<?> entityType, final Object entity) throws Exception {
		final SingularAttribute<?, ?> idAttribute = entityType.getId(entityType.getIdType().getJavaType());
		final Field idField = entity.getClass().getField(idAttribute.getName());
		idField.setAccessible(true);
		final Object entityId = idField.get(entity);
		return entityId;
	}
}
