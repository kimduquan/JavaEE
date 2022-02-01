package epf.persistence.util;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.EmbeddableType;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;

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
	
	/**
	 * @param metamodel
	 * @param name
	 * @return
	 */
	static EntityType<?> findEntityType(final Metamodel metamodel, final String name){
		return metamodel.getEntities()
				.stream()
				.filter(type -> type.getName().equals(name))
				.findFirst()
				.orElseThrow(NotFoundException::new);
	}
	
	/**
	 * @param <T>
	 * @param metamodel
	 * @return
	 */
	static <T> Stream<Entity<T>> getEntities(final Metamodel metamodel) {
		return metamodel
				.getEntities()
    			.stream()
    			.map(entityType -> { 
    					Entity<T> entity = null;
    					try {
                    		@SuppressWarnings("unchecked")
                    		final EntityType<T> type = entityType.getClass().cast(entityType);
                    		entity = new Entity<>();
                    		entity.setType(type);
    	                	}
                    	catch(Exception ex) {
                    		throw ex;
                    	}
    					return entity;
    				}
    			)
    			.filter(entity -> entity != null);
	}
	
	/**
	 * @param <T>
	 * @param metamodel
	 * @return
	 */
	static <T> Stream<Embeddable<T>> getEmbeddables(final Metamodel metamodel){
		return metamodel
				.getEmbeddables()
    			.stream()
    			.map(embeddableType -> { 
    					Embeddable<T> embeddable = null;
    					try {
                    		@SuppressWarnings("unchecked")
                    		final EmbeddableType<T> type = embeddableType.getClass().cast(embeddableType);
                    		embeddable = new Embeddable<>();
                    		embeddable.setType(type);
    	                	}
                    	catch(Exception ex) {
                    		throw ex;
                    	}
    					return embeddable;
    				}
    			)
    			.filter(embeddable -> embeddable != null);
	}
	
	/**
	 * @param metamodel
	 * @param entityTables
	 * @param entityAttributes
	 */
	static void mapEntities(
			final Metamodel metamodel,
			final Map<String, EntityType<?>> entityTables, 
    		final Map<String, Map<String, Attribute<?,?>>> entityAttributes) {
		metamodel
		.getEntities()
		.forEach(entityType -> {
			String tableName = entityType.getName().toLowerCase(Locale.getDefault());
			final Table tableAnnotation = entityType.getJavaType().getAnnotation(Table.class);
			if(tableAnnotation != null) {
				tableName = tableAnnotation.name();
			}
			entityTables.put(tableName, entityType);
			final Map<String, Attribute<?,?>> attributes = new ConcurrentHashMap<>();
			entityType.getAttributes().forEach(attr -> {
				String columnName = attr.getName().toLowerCase(Locale.getDefault());
				if(attr.getJavaMember() instanceof Field) {
					final Field field = (Field) attr.getJavaMember();
					final Column columnAnnotation = field.getAnnotation(Column.class);
					if(columnAnnotation != null) {
						columnName = columnAnnotation.name();
					}
				}
				attributes.put(columnName, attr);
			});
			entityAttributes.put(tableName, attributes);
		});
	}
}
