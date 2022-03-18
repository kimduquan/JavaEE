package epf.persistence.internal.util;

import java.io.InputStream;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.persistence.metamodel.EntityType;
import javax.ws.rs.BadRequestException;

/**
 * @author PC
 *
 */
public interface EntityUtil {

	/**
	 * @param entityType
	 * @param id
	 * @return
	 */
	static Object getEntityId(final EntityType<?> entityType, final String id) {
    	Object entityId = id;
    	try {
	    	switch(entityType.getIdType().getJavaType().getName()) {
	    		case "java.lang.Integer":
	    			entityId = Integer.valueOf(id);
	    			break;
	    		case "java.lang.Long":
	    			entityId = Long.valueOf(id);
	    			break;
	    		default:
	    			break;
	    	}
    	}
    	catch(NumberFormatException ex) {
    		throw new BadRequestException(ex);
    	}
    	return entityId;
    }
	
	/**
	 * @param entityType
	 * @param body
	 * @return
	 */
	static Object toObject(final EntityType<?> entityType, final InputStream body) {
    	try(Jsonb json = JsonbBuilder.create()){
        	return json.fromJson(body, entityType.getJavaType());
        }
    	catch(Exception ex){
        	throw new BadRequestException(ex);
        }
    }
}
