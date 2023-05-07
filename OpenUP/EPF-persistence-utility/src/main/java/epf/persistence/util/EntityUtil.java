package epf.persistence.util;

/**
 * @author PC
 *
 */
public interface EntityUtil {
	
	/**
	 * @param entityIdFieldType
	 * @param id
	 * @return
	 */
	static Object getEntityId(final String entityIdFieldType, final Object id) {
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
}